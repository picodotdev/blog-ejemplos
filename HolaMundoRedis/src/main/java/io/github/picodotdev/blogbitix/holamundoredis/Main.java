package io.github.picodotdev.blogbitix.holamundoredis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    private Jedis jedis;

    @Bean
    public Jedis buildJedis() {
        return new Jedis("localhost");
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        jedis.del("string", "set", "sortedSet", "list", "hash", "hello");

        // Strings
        jedis.set("string", "Hello World!");
        System.out.printf("String: %s%n", jedis.get("string"));

        // Sets
        jedis.sadd("set", "string1", "string2", "string3");
        System.out.printf("Set: %s%n", jedis.smembers("set"));

        // Sorted sets
        Map<String, Double> sortedSet = new HashMap<>();
        sortedSet.put("string1", 1d);
        sortedSet.put("string2", 2d);
        sortedSet.put("string3", 3d);
        jedis.zadd("sortedSet", sortedSet);
        System.out.printf("SortedSet: %s%n", jedis.zrange("sortedSet", 0, -1));

        // Lists
        jedis.rpush("list", "string1", "string2", "string3");
        System.out.printf("List: %s%n", jedis.lrange("list", 0, -1));

        // Hashes
        Map<String, String> hash = new HashMap<>();
        hash.put("property1", "string1");
        hash.put("property2", "string2");
        hash.put("property3", "string3");
        jedis.hmset("hash", hash);
        System.out.printf("Hash: %s%n", jedis.hgetAll("hash"));

        // Keys
        jedis.rename("string", "hello");
        jedis.expire("hello", 10);
        System.out.printf("Type: %s%n", jedis.type("set"));
        jedis.del("hello");
        System.out.printf("Keys: %s%n",jedis.keys("*"));
    }
}
