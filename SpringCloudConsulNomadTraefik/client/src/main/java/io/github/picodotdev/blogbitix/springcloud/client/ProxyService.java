package io.github.picodotdev.blogbitix.springcloud.client;

import brave.Span;
import brave.Tracer;
import brave.Tracing;
import brave.propagation.TraceContext;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

@Component
public class ProxyService {

    @Autowired
    private LoadBalancerClient loadBalancer;

    @Autowired
    private Tracing tracing;

    @Autowired
    private Tracer tracer;

    @Autowired
    private RestTemplate restTemplate;

    private CircuitBreakerConfig circuitBreakerConfiguration;
    private TimeLimiterConfig timeLimiterConfiguration;
    private HttpClient client;

    public ProxyService() {
        circuitBreakerConfiguration = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .recordExceptions(IOException.class, TimeoutException.class)
                .build();

        timeLimiterConfiguration = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofMillis(2500))
                .cancelRunningFuture(true)
                .build();

        client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
    }

    public String get() {
        ServiceInstance instance = loadBalancer.choose("traefik");
        URI uri = instance.getUri();
        String resource = String.format("%s%s", uri.toString().replace("127.0.0.1", "traefik"), "/service");
        final URI resourceUri = URI.create(resource);

        CircuitBreaker circuitBreaker = CircuitBreaker.of("resilience4jCircuitBreakerProxyService", circuitBreakerConfiguration);
        TimeLimiter timeLimiter = TimeLimiter.of(timeLimiterConfiguration);

        Supplier<CompletableFuture<String>> get = () -> {
            return CompletableFuture.supplyAsync(() -> {
                Span span = tracer.newTrace().kind(Span.Kind.CLIENT).name("CLIENT").start();
                System.out.printf("Client Span (traceId: %s, spanId: %s)%n", span.context().traceIdString(), span.context().spanIdString());

                String result = getRequest(client, span, resourceUri);
                //String result = getRequest(restTemplate, span, resouceUri);

                span.finish();
                return result;
            });
        };
        Callable<String> getLimiter = TimeLimiter.decorateFutureSupplier(timeLimiter, get);
        Callable<String> getCircuitBreaker = CircuitBreaker.decorateCallable(circuitBreaker, getLimiter);

        return Try.of(getCircuitBreaker::call).recover((throwable) -> getFallback()).get();
    }

    private String getFallback() {
        return "Fallback";
    }

    private String getRequest(HttpClient client, Span span, URI resourceUri) {
        HttpRequest.Builder request = HttpRequest.newBuilder(resourceUri).GET();
        Span serviceSpan = tracer.newChild(span.context());

        try (Tracer.SpanInScope ws = tracer.withSpanInScope(serviceSpan)) {
            TraceContext.Injector<HttpRequest.Builder> injector = tracing.propagation().injector((HttpRequest.Builder carrier, String key, String value) -> {
                carrier.header(key, value);
            });
            injector.inject(tracer.currentSpan().context(), request);

            HttpResponse<String> response = client.send(request.build(), HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            return getFallback();
        } finally {
            serviceSpan.finish();
        }
    }

    private String getRequest(RestTemplate restTemplate, Span span, URI resourceUri) {
        try (Tracer.SpanInScope ws = tracer.withSpanInScope(span)) {
            return restTemplate.getForObject(resourceUri, String.class);
        } catch (RestClientException e) {
            return getFallback();
        }
    }
}
