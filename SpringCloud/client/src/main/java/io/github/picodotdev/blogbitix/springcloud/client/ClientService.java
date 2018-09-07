package io.github.picodotdev.blogbitix.springcloud.client;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sun.jersey.api.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class ClientService {

    @Autowired
    private LoadBalancerClient loadBalancer;

    @HystrixCommand(fallbackMethod = "getFallback")
    public String get() {
        ServiceInstance instance = loadBalancer.choose("service");
        URI uri = instance.getUri();
        return Client.create().resource(uri).get(String.class);
    }

    private String getFallback() {
        return "Fallback";
    }
}
