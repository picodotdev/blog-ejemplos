package io.github.picodotdev.blogbitix.httpclientlog;

import codes.rafael.interceptablehttpclient.InterceptableHttpClient;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    private static Map<HttpClient.Version, String> VERSION = Map.of(HttpClient.Version.HTTP_1_1, "--http1.1", HttpClient.Version.HTTP_2, "--http2");

    public interface GoogleService {
        @GET("/")
        @Headers("User-Agent: java/1.0")
        Call<String> get();
    }

    public void httpClient() throws Exception {
        Function<HttpRequest, String> onRequest = (HttpRequest r) -> {
            String headers = r.headers().map().entrySet().stream().map((e) -> {
                return String.format("-H \"%s: %s\"", e.getKey(), e.getValue().stream().collect(Collectors.joining(",")));
            }).collect(Collectors.joining(","));
            return String.format("curl -v %s -X %s %s %s", VERSION.get(r.version().orElse(HttpClient.Version.HTTP_2)), r.method().toUpperCase(), headers, r.uri());
        };
        BiConsumer<HttpResponse<?>, String> onResponse = (HttpResponse<?> r, String curl) -> {
            String headers = r.headers().map().entrySet().stream().map((e) -> {
                return String.format("[%s: %s]", e.getKey(), e.getValue().stream().collect(Collectors.joining(",")));
            }).collect(Collectors.joining(","));
            System.out.printf("%s%n", curl);
            System.out.printf("%s %s%n", r.statusCode(), headers);
        };
        BiConsumer<Throwable, String> onError = (Throwable t, String curl) -> {
            System.out.printf("%s%n", curl);
            t.printStackTrace();
        };

        HttpClient client = InterceptableHttpClient.builder().version(HttpClient.Version.HTTP_2).interceptor(onRequest, onResponse, onError).build();

        HttpResponse<String> response = client.send(HttpRequest.newBuilder(new URI("https://www.google.es/")).headers("User-Agent", "java/1.0").GET().build(), HttpResponse.BodyHandlers.ofString());
    }

    public void webClient() {
        ExchangeFilterFunction logRequest = ExchangeFilterFunction.ofRequestProcessor(r -> {
            String headers = r.headers().entrySet().stream().map((e) -> {
                return String.format("-H \"%s: %s\"", e.getKey(), e.getValue().stream().collect(Collectors.joining(",")));
            }).collect(Collectors.joining(","));
            System.out.printf("curl -v %s -X %s %s %s%n", HttpClient.Version.HTTP_2, r.method().name().toUpperCase(), headers, r.url());
            return Mono.just(r);
        });

        ExchangeFilterFunction logRespose = ExchangeFilterFunction.ofResponseProcessor(r -> {
            String headers = r.headers().asHttpHeaders().entrySet().stream().map((e) -> {
                return String.format("-H \"%s: %s\"", e.getKey(), e.getValue().stream().collect(Collectors.joining(",")));
            }).collect(Collectors.joining(","));
            System.out.printf("%s %s%n", r.statusCode(), headers);
            return Mono.just(r);
        });

        WebClient client = WebClient.builder().filters(f -> {
            f.add(logRequest);
            f.add(logRespose);
        }).baseUrl("https://www.google.com/").build();
        client.get().uri("/").header("User-Agent", "java/1.0").retrieve().toEntity(String.class).block();
    }

    public void retrofit() throws Exception {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                logRequest(chain.request());
                Response response = chain.proceed(chain.request());
                logResponse(response);
                return response;
            }

            private void logRequest(Request r) throws IOException {
                String headers = r.headers().toMultimap().entrySet().stream().map((e) -> {
                    return String.format("-H \"%s: %s\"", e.getKey(), e.getValue().stream().collect(Collectors.joining(",")));
                }).collect(Collectors.joining(","));
                System.out.printf("curl -v %s -X %s %s %s%n", HttpClient.Version.HTTP_2, r.method().toUpperCase(), headers, r.url());
            }

            private void logResponse(Response r) throws IOException {
                String headers = r.headers().toMultimap().entrySet().stream().map((e) -> {
                    return String.format("-H \"%s: %s\"", e.getKey(), e.getValue().stream().collect(Collectors.joining(",")));
                }).collect(Collectors.joining(","));
                System.out.printf("%s %s%n", r.code(), headers);
            }
        };

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.google.com/")
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        GoogleService service = retrofit.create(GoogleService.class);
        service.get().execute();
        client.dispatcher().executorService().shutdown();
        client.connectionPool().evictAll();
    }

    public void okHttp() throws Exception {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                logRequest(chain.request());
                Response response = chain.proceed(chain.request());
                logResponse(response);
                return response;
            }

            private void logRequest(Request r) throws IOException {
                String headers = r.headers().toMultimap().entrySet().stream().map((e) -> {
                    return String.format("-H \"%s: %s\"", e.getKey(), e.getValue().stream().collect(Collectors.joining(",")));
                }).collect(Collectors.joining(","));
                System.out.printf("curl -v %s -X %s %s %s%n", HttpClient.Version.HTTP_2, r.method().toUpperCase(), headers, r.url());
            }

            private void logResponse(Response r) throws IOException {
                String headers = r.headers().toMultimap().entrySet().stream().map((e) -> {
                    return String.format("-H \"%s: %s\"", e.getKey(), e.getValue().stream().collect(Collectors.joining(",")));
                }).collect(Collectors.joining(","));
                System.out.printf("%s %s%n", r.code(), headers);
            }
        };

        Request request = new Request.Builder()
                .url("https://www.google.com/")
                .addHeader("User-Agent", "java/1.0")
                .build();
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        client.newCall(request).execute().close();
        client.dispatcher().executorService().shutdown();
        client.connectionPool().evictAll();
    }

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        System.out.println("HttpClient");
        main.httpClient();
        System.out.println();
        System.out.println("WebClient");
        main.webClient();
        System.out.println();
        System.out.println("Retrofit");
        main.retrofit();
        System.out.println();
        System.out.println("OkHttp");
        main.okHttp();
    }
}
