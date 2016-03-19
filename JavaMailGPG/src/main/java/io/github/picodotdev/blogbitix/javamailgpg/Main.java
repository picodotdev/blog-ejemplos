package io.github.picodotdev.blogbitix.javamailgpg;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Main {

    public static void main(String[] args) throws Exception {
        String gmailPassword = args[0];
        
        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("pico.dev@gmail.com", gmailPassword);
            }
        };

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.googlemail.com");
        properties.put("mail.smtp.port", "587");

        // Contenido del mensaje
        String content = "Hola mundo!\n";

        // Establecer las direcciones a las que será enviado el mensaje
        MimeBodyPart contentPart = new MimeBodyPart();
        contentPart.setText(content, "UTF-8");

        // Firmar el contenido con GPG
        String signature = execute(new String[] { "gpg", "--armor", "--detach-sig", "-u", "pico.dev@gmail.com" }, content);

        MimeBodyPart signaturePart = new MimeBodyPart();
        signaturePart.setContent(signature, "application/pgp-signature");
        signaturePart.setHeader("Content-Type", "application/pgp-signature; " + "name=signature.asc");

        // Agrupar las partes
        Multipart mp = new MimeMultipart();
        mp.addBodyPart(contentPart);
        mp.addBodyPart(signaturePart);

        // Obtener la sesión para enviar correos electrónicos
        Session session = Session.getDefaultInstance(properties, authenticator);

        // Crear el mensaje a enviar
        MimeMessage message = new MimeMessage(session);
        message.setSubject("Hola mundo!", "UTF-8");
        message.setFrom(new InternetAddress("pico.dev@gmail.com"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress("pico.dev@gmail.com"));
        message.addRecipient(Message.RecipientType.BCC, new InternetAddress("pico.dev@gmail.com"));
        message.setContent(mp);

        // Enviar el correo electrónico
        Transport.send(message);
    }

    private static String execute(String[] command, String input) throws Exception {
        Process process = Runtime.getRuntime().exec(command);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream(), "UTF-8"));
        writer.write(input);
        writer.close();

        process.waitFor(10, TimeUnit.SECONDS);
        int ev = process.exitValue();

        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String success = br.lines().collect(Collectors.joining("\n"));
        br.close();

        InputStream eis = process.getErrorStream();
        InputStreamReader eisr = new InputStreamReader(eis);
        BufferedReader ebr = new BufferedReader(eisr);
        String error = ebr.lines().collect(Collectors.joining("\n"));
        ebr.close();

        if (ev != 0) {
            throw new Exception(error);
        }

        return success;
    }
}
