package io.github.picodotdev.blogbitix.testcontainers;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import redis.clients.jedis.Jedis;

@SpringBootTest
@Testcontainers
@ContextConfiguration(initializers = { RedisTest.Initializer.class })
public class RedisTest {

    @Container
    private GenericContainer redis = new GenericContainer<>("redis:6").withExposedPorts(6379);

    private Jedis jedis;

    @BeforeEach
    void beforeEach() {
        String host = redis.getHost();
        Integer port = redis.getFirstMappedPort();

        jedis = new Jedis(host, port);
    }

    @Test
    void redisTest() {
        jedis.set("foo", "bar");

        Assert.assertEquals("bar", jedis.get("foo"));
    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of("spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration")
                    .applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}