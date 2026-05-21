package io.github.picodotdev.blogbitix.springcloudvaultcipher;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.vault.core.VaultOperations;

@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    private VaultOperations vault;

    @Override
    public void run(String... args) {
        String plaintext = "Hello World!";
        String encrypted = vault.opsForTransit().encrypt("app", plaintext);
        String decrypted = vault.opsForTransit().decrypt("app", encrypted);

        System.out.println("Plaintext: " + plaintext);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class, args);
    }
}
