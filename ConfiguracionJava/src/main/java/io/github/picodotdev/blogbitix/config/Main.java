package io.github.picodotdev.blogbitix.config;

import java.nio.file.FileSystems;
import java.nio.file.Path;

public class Main {

	public static void main(String[] args) throws Exception {
		Path path = FileSystems.getDefault().getPath("src/main/java/io/github/picodotdev/blogbitix/config/AppConfiguracion.java");
		ConfiguracionManager manager = new ConfiguracionManager("io.github.picodotdev.blogbitix.config.AppConfiguracion", path).load().monitor();
		
		int n = 0;
		while (n < 20) {
			Thread.sleep(2000);
			System.out.println(manager.get());
		}
	}
}