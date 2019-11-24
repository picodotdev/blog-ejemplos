package io.github.picotodtdev.blogbitix.useragent;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.blueconic.browscap.UserAgentParser;
import com.blueconic.browscap.UserAgentService;
import com.blueconic.browscap.ParseException;

@SpringBootApplication
public class Main {

	@Bean
	public UserAgentParser userAgentParser() throws IOException, ParseException {
		return new UserAgentService().loadParser();
	}

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

}
