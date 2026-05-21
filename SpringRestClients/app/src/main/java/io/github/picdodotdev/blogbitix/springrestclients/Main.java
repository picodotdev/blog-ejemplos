package io.github.picdodotdev.blogbitix.springrestclients;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.client.reactive.JdkClientHttpConnector;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class Main implements CommandLineRunner {

	@Override
	public void run(String... args) {
		restTemplate();
		webClient();
		httpInterface();
	}

	private void restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		String message1 = restTemplate.getForObject("http://localhost:8080/message/", String.class);
		String message2 = restTemplate.getForObject("http://localhost:8080/message/{name}", String.class, "picodotdev");

		System.out.println("RestTemplate (message1): " + message1);
		System.out.println("RestTemplate (message2): " + message2);
	}

	private void webClient() {
		ExchangeFilterFunction filter = (ClientRequest request, ExchangeFunction next) -> {
			System.out.println("Filter " + request.url().getPath());
			return next.exchange(request);
		};
		WebClient webClient = WebClient.builder().clientConnector(new JdkClientHttpConnector()).filter(filter).baseUrl("http://localhost:8080").build();

		String message1 = webClient.get().uri("/message/").retrieve().bodyToMono(String.class).block();
		String message2 = webClient.get().uri("/message/{name}",  "picodotdev").retrieve().bodyToMono(String.class).block();

		System.out.println("WebClient (message1): " + message1);
		System.out.println("WebClient (message2): " + message2);
	}

	private void httpInterface() {
		WebClient client = WebClient.builder().clientConnector(new JdkClientHttpConnector()).baseUrl("http://localhost:8080").build();
		HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client)).build();

		MessageService messageService = factory.createClient(MessageService.class);
		String message1 = messageService.message();
		String message2 = messageService.message("picodotdev");

		System.out.println("HttpInterface (message1): " + message1);
		System.out.println("HttpInterface (message2): " + message2);
	}

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}
