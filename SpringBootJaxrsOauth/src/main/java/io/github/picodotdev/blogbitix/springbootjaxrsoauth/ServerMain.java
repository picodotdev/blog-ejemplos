package io.github.picodotdev.blogbitix.springbootjaxrsoauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

@SpringBootApplication
public class ServerMain {

	@Bean
	MessageService buillMessageService() {
		return new DefaultMessageService();
	}

	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {
		return new EmbeddedServletContainerCustomizer() {
			@Override
			public void customize(ConfigurableEmbeddedServletContainer container) {
				container.addErrorPages(new ErrorPage(HttpStatus.UNAUTHORIZED, "/401"));
				container.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/403"));
				container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404"));
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(ServerMain.class, args);
	}
}
