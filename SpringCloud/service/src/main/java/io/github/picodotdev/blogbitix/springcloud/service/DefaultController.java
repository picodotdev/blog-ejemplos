package io.github.picodotdev.blogbitix.springcloud.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

	@RequestMapping("/")
	public String home() {
		return "Hello world";
	}
}
