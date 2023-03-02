package io.github.picodotdev.blogbitix.javahashingencrypt;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.KeySpec;
import java.time.Duration;
import java.util.HexFormat;
import java.util.Set;
import java.util.stream.Collectors;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Hashing");
        hashing();

        System.out.println("");
        System.out.println("Symetric encryption");
        symetricEncrypt();
    }

    private static void hashing() throws Exception {
        Set<String> messageDigest = Security.getAlgorithms("MessageDigest");
        System.out.println("Supported algorithms: " + messageDigest.stream().sorted().collect(Collectors.joining(",")));

        String password = "rw@wbnaq2R@DS#u3o7hxWckqhfkzbT";

        System.out.println("Plain text: " + password);
        System.out.println("MD5: " + calculateHash("MD5", password));
        System.out.println("SHA-1: " + calculateHash("SHA-1", password));
        System.out.println("SHA-256: " + calculateHash("SHA-256", password));
        System.out.println("SHA-512: " + calculateHash("SHA-512", password));
        System.out.println("SHA3-256: " + calculateHash("SHA3-256", password));
        System.out.println("SHA3-512: " + calculateHash("SHA3-512", password));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://mirror.rackspace.com/archlinux/iso/latest/archlinux-bootstrap-x86_64.tar.gz"))
                .timeout(Duration.ofMinutes(2))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse<InputStream> response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofInputStream());
        HttpRequest shasumRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://mirror.rackspace.com/archlinux/iso/latest/sha256sums.txt"))
                .timeout(Duration.ofMinutes(2))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse<String> shasumResponse = HttpClient.newBuilder().build().send(shasumRequest, HttpResponse.BodyHandlers.ofString());

        String shasum = shasumResponse.body().lines().skip(3).findFirst().get().split(" ")[0];
        System.out.println("Arch Linux ISO (SHA-256): " + shasum);

        String shasumCalculated = calculateHash("SHA-256", response.body());
        System.out.println("SHA-256: " + shasumCalculated);
        System.out.println("sha256sum matches: " + shasum.equals(shasumCalculated));
    }

    private static void symetricEncrypt() throws Exception {
        Set<String> keyGenerators = Security.getAlgorithms("KeyGenerator");
        System.out.println("Supported key generators: " + keyGenerators.stream().sorted().collect(Collectors.joining(",")));

        Set<String> secretKeyFactories = Security.getAlgorithms("SecretKeyFactory");
        System.out.println("Supported key factory: " + secretKeyFactories.stream().sorted().collect(Collectors.joining(",")));

        Set<String> chipers = Security.getAlgorithms("Cipher");
        System.out.println("Supported ciphers: " + chipers.stream().sorted().collect(Collectors.joining(",")));

        Set<String> macs = Security.getAlgorithms("Mac");
        System.out.println("Supported macs: " + macs.stream().sorted().collect(Collectors.joining(",")));

        String text = "rw@wbnaq2R@DS#u3o7hxWckqhfkzbT";
        String password = "rw@wbnaq2R@DS#u3o7hxWckqhfkzbT";
        String salt = "%@&LN4CLT95yMEHNSettCAaQAcHZbh";

        SecretKey key = generateKey();
        SecretKey passwordKey = generateKey(password, salt);

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] keyEncrypted = encrypt(key, text);
        byte[] passwordEncrypted = encrypt(passwordKey, text);
        byte[] inputStreamEncrypted = encrypt(key, Main.class.getResourceAsStream("/text.txt"));

        System.out.println("Plain text: " + password);
        System.out.println("Key encrypted: " + HexFormat.of().formatHex(keyEncrypted));
        System.out.println("Password key encrypted: " + HexFormat.of().formatHex(passwordEncrypted));
        System.out.println("InputStream key encrypted: " + HexFormat.of().formatHex(inputStreamEncrypted));
        System.out.println("Key decrypted: " + new String(decrypt(key, keyEncrypted)));
        System.out.println("Password key decrypted: " + new String(decrypt(passwordKey, passwordEncrypted)));
        System.out.println("HMAC: " + calculateHmac(key, text));
    }

    private static String calculateHash(String algorithm, String content) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        messageDigest.update(content.getBytes(StandardCharsets.UTF_8));
        byte[] hash = messageDigest.digest();
        return HexFormat.of().formatHex(hash);
    }

    private static String calculateHash(String algorithm, InputStream stream) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        try (
            InputStream is = new BufferedInputStream(stream, 10 * 1024 * 1024);
            DigestInputStream digestInputStream = new DigestInputStream(is, messageDigest)
        ) {
            digestInputStream.transferTo(OutputStream.nullOutputStream());
            byte[] hash = messageDigest.digest();
            return HexFormat.of().formatHex(hash);
        }
    }

    private static SecretKey generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        return keyGenerator.generateKey();
    }

    private static SecretKey generateKey(String password, String salt) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }

    private static byte[] encrypt(SecretKey key, String text) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(text.getBytes());
    }

    private static byte[] encrypt(SecretKey key, InputStream stream) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        CipherInputStream cipherInputStream = new CipherInputStream(stream, cipher);
        return cipherInputStream.readAllBytes();
    }

    private static byte[] decrypt(SecretKey key, byte[] encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(encrypted);
    }

    public static String calculateHmac(SecretKey key, String text) throws Exception {
        Mac mac = Mac.getInstance("HMACSHA256");
        mac.init(key);
        byte[] bytes = mac.doFinal(text.getBytes());
        return HexFormat.of().formatHex(bytes);
    }
}

