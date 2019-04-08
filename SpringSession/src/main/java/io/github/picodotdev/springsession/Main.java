package io.github.picodotdev.springsession;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.session.data.redis.DefaultRedisOperationSessionRespository;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.DefaultCookieSerializer;

@SpringBootApplication
@EnableRedisHttpSession
@ComponentScan("io.github.picodotdev.springsession")
public class Main {

	private static final int SESSION_ID_LENGTH = 64;

//	@Bean
//	public WebServerFactoryCustomizer<TomcatServletWebServerFactory> cookieProcessorCustomizer() {
//		return (TomcatServletWebServerFactory factory) -> {
//			factory.addContextCustomizers((Context context) -> {
//				if (context.getManager() == null) {
//					context.setManager(new StandardManager());
//				}
//				context.getManager().getSessionIdGenerator().setSessionIdLength(SESSION_ID_LENGTH);
//			});
//		};
//	}

	@Bean
	public DefaultCookieSerializer cookieSerializer() {
		DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
		cookieSerializer.setUseBase64Encoding(false);
		return cookieSerializer;
	}

	@Bean
	@Primary
	public RedisOperationsSessionRepository defaultSessionRepository(RedisOperations<Object, Object> sessionRedisOperations) {
		return new DefaultRedisOperationSessionRespository(sessionRedisOperations);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Main.class, args);
	}
}