package io.github.picodotdev.blogbitix.springcloud.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

@RestController
public class DefaultController {

	@Autowired
	private DefaultConfiguration configuration;

	private Random random;
	private Counter counter;

	public DefaultController(MeterRegistry registry) {
		this.random = new Random();
		this.counter = Counter.builder("service.invocations").description("Total service invocations").register(registry);
	}

	@RequestMapping("/")
	public String home(HttpServletRequest request) throws Exception {
		counter.increment();

		// Timeout simulation
		//Thread.sleep(random.nextInt(2000));

		return String.format("Hello world (%s, %s)", request.getRequestURL(), configuration.getKey());
	}
}
