package io.github.picodotdev.blogbitix.springcloud.service;

import brave.Span;
import brave.Tracer;
import brave.Tracing;
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

    @Autowired
    private Tracing tracing;

    @Autowired
    private Tracer tracer;

    private Random random;
    private Counter counter;

    public DefaultController(MeterRegistry registry) {
        this.random = new Random();
        this.counter = Counter.builder("service.invocations").description("Total service invocations").register(registry);
    }

    @RequestMapping("/")
    public String home(HttpServletRequest request) throws Exception {
        Span span = tracer.currentSpan();

        System.out.printf("Service Span (traceId: %s, spanId: %s)%n", span.context().traceIdString(), span.context().spanIdString());
        counter.increment();

        // Timeout simulation
        //Thread.sleep(random.nextInt(4000));

        return String.format("Hello world (url: %s, remoteAddress_%s, localAddress: %s, traceId: %s, spanId: %s, key: %s)", request.getRequestURL(),
                request.getRemoteAddr(), request.getLocalAddr(), span.context().traceIdString(), span.context().spanIdString(), configuration.getKey());
    }
}
