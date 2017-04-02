package io.github.picodotdev.blogbitix.dockerswarm;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.Collectors;

@SpringBootApplication
public class Main implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		Path file = FileSystems.getDefault().getPath("/data/timestamp");

		if (file.toFile().exists()) {
			System.out.printf("Último inicio %s\n", Files.lines(file).limit(1).collect(Collectors.joining("\n")));
		} else {
			System.out.println("No hay información de último inicio ");
		}
		if (file.getParent().toFile().exists() && file.getParent().toFile().canWrite()) {
			Files.write(file, Arrays.asList(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)), Charset.forName("UTF-8"));
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}
