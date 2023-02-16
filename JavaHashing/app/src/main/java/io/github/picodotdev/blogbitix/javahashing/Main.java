package io.github.picodotdev.blogbitix.javahashing;

import java.io.IOException;
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
import java.time.Duration;
import java.util.HexFormat;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws Exception {
        Set<String> messageDigest = Security.getAlgorithms("MessageDigest");
        System.out.println("Supported algorithms: " + messageDigest.stream().sorted().collect(Collectors.joining(",")));

        String password = "rw@wbnaq2R@DS#u3o7hxWckqhfkzbT";

        System.out.printf("%s: %s%n", "MD5", calculateHash("MD5", password));
        System.out.printf("%s: %s%n", "SHA-1", calculateHash("SHA-1", password));
        System.out.printf("%s: %s%n", "SHA-256", calculateHash("SHA-256", password));
        System.out.printf("%s: %s%n", "SHA-512", calculateHash("SHA-512", password));
        System.out.printf("%s: %s%n", "SHA3-256", calculateHash("SHA3-256", password));
        System.out.printf("%s: %s%n", "SHA3-512", calculateHash("SHA3-512", password));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://ftp.rediris.es/mirror/archlinux/iso/latest/archlinux-x86_64.iso"))
                .timeout(Duration.ofMinutes(2))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse<InputStream> response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofInputStream());
        HttpRequest shasumRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://ftp.rediris.es/mirror/archlinux/iso/latest/sha256sums.txt"))
                .timeout(Duration.ofMinutes(2))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse<String> shasumResponse = HttpClient.newBuilder().build().send(shasumRequest, HttpResponse.BodyHandlers.ofString());

        String shasum = shasumResponse.body().split(" ")[0];
        System.out.printf("Arch Linux ISO (%s): %s%n", "SHA-256", shasum);

        String shasumCalculated = calculateHash("SHA-256", response.body());
        System.out.printf("%s: %s%n", "SHA-256", shasumCalculated);
        System.out.printf("sha256sum matches: %s%n", shasum.equals(shasumCalculated));
    }

    private static String calculateHash(String algorithm, String content) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        messageDigest.update(content.getBytes(StandardCharsets.UTF_8));
        byte[] hash = messageDigest.digest();
        return HexFormat.of().formatHex(hash);
    }

    private static String calculateHash(String algorithm, InputStream stream) throws IOException, NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        try (DigestInputStream digestInputStream = new DigestInputStream(stream, messageDigest)) {
            digestInputStream.transferTo(OutputStream.nullOutputStream());
            byte[] hash = messageDigest.digest();
            return HexFormat.of().formatHex(hash);
        }
    }
}
