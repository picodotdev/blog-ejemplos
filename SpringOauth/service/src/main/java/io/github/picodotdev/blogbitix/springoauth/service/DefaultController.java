package io.github.picodotdev.blogbitix.springoauth.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

@RestController
public class DefaultController {

	private Random random;

	public DefaultController() {
		this.random = new Random();
	}

	@RequestMapping("/")
	public String home(HttpServletRequest request) throws Exception {
		return String.format("Hello world (%s)", request.getRequestURL());
	}
}
