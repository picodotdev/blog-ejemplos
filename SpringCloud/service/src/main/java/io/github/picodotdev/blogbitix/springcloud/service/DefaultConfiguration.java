package io.github.picodotdev.blogbitix.springcloud.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

// curl -i -X POST  http://localhost:8080/actuator/refresh
@Configuration
@RefreshScope
public class DefaultConfiguration {

    @Value("${config.key}")
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
