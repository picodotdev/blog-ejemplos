package io.github.picodotdev.blogbitix.javahashingencrypt;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.DigestInputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
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

import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.util.io.pem.PemObject;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Hashing");
        hashing();

        System.out.println("");
        System.out.println("Symmetric encryption");
        symmetricEncrypt();

        System.out.println("");
        System.out.println("Asymmetric encryption");
        asymmetricEncrypt();
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

    private static void symmetricEncrypt() throws Exception {
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

    private static void asymmetricEncrypt() throws Exception {
        Set<String> keypairGenerators = Security.getAlgorithms("KeyPairGenerator");
        System.out.println("Supported key generators: " + keypairGenerators.stream().sorted().collect(Collectors.joining(",")));

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(8192);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        System.out.println("Private key: \n" + encodePem(privateKey));
        System.out.println("Public key: \n" + encodePem(publicKey));

        String text = "rw@wbnaq2R@DS#u3o7hxWckqhfkzbT";

        byte[] encrypted = encrypt(publicKey, text);
        String decrypted = new String(decrypt(privateKey, encrypted));

        System.out.println("Plain text: " + text);
        System.out.println("Public key encrypted: " + HexFormat.of().formatHex(encrypted));
        System.out.println("Private key decrypted: " + decrypted);
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

    private static byte[] encrypt(PublicKey publicKey, String text) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(text.getBytes());
    }

    private static byte[] decrypt(SecretKey key, byte[] encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(encrypted);
    }

    private static byte[] decrypt(PrivateKey key, byte[] encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(encrypted);
    }

    public static String calculateHmac(SecretKey key, String text) throws Exception {
        Mac mac = Mac.getInstance("HMACSHA256");
        mac.init(key);
        byte[] bytes = mac.doFinal(text.getBytes());
        return HexFormat.of().formatHex(bytes);
    }

    public static String encodePem(PrivateKey privateKey) throws Exception {
        PemObject privateKeyPemObject = new PemObject("RSA PRIVATE KEY", privateKey.getEncoded());
        StringWriter stringWriter = new StringWriter();
        JcaPEMWriter pemWriter = new JcaPEMWriter(stringWriter);
        pemWriter.writeObject(privateKeyPemObject);
        pemWriter.close();
        return stringWriter.toString();
    }

    public static String encodePem(PublicKey publicKey) throws Exception {
        PemObject privateKeyPemObject = new PemObject("RSA PUBLIC KEY", publicKey.getEncoded());
        StringWriter stringWriter = new StringWriter();
        JcaPEMWriter pemWriter = new JcaPEMWriter(stringWriter);
        pemWriter.writeObject(privateKeyPemObject);
        pemWriter.close();
        return stringWriter.toString();
    }
}

