package io.github.picodotdev.blogbitix.springbootconfigconditional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.picodotdev.blogbitix.springbootconfigconditional.conditional.OperatingSystem;
import io.github.picodotdev.blogbitix.springbootconfigconditional.service.Message;

@SpringBootApplication
public class Main implements CommandLineRunner {

  @Autowired
  private Message message;

  @Autowired
  private OperatingSystem os;

  @Override
  public void run(String... args) throws Exception {
    System.out.println("Operating system: " + os.getName());
    System.out.println("Message: " + message.get());
  }

  public static void main(String[] args) throws Exception {
    SpringApplication.run(Main.class, args);
  }
}
