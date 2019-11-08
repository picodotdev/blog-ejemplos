package io.github.picodotdev.blogbitix.jmx.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.EnableMBeanExport;

import io.github.picodotdev.blogbitix.jmx.mbean.Hello;

@SpringBootApplication(scanBasePackageClasses = {Hello.class})
@EnableMBeanExport
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
