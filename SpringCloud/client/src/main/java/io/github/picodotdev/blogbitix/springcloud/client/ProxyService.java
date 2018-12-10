package io.github.picodotdev.blogbitix.springcloud.client;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;

import javax.ws.rs.client.ClientBuilder;
import java.net.URI;

@Component
public class ProxyService {

    @Autowired
    private LoadBalancerClient loadBalancer;

    @HystrixCommand(fallbackMethod = "getFallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "4"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "25000")
    })
    public String get() {
        ServiceInstance instance = loadBalancer.choose("proxy");
        URI uri = instance.getUri();
        String resource = String.format("%s%s", uri.toString(), "/service");
        return ClientBuilder.newClient().target(resource).request().get().readEntity(String.class);
    }

    private String getFallback() {
        return "Fallback";
    }
}
