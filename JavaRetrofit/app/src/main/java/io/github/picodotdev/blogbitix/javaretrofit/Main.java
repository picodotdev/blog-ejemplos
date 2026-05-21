package io.github.picodotdev.blogbitix.javaretrofit;

import java.util.UUID;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.okhttp3.OkHttpMetricsEventListener;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    private MeterRegistry registry;

    @Override
    public void run(String... args) throws Exception {
        Service service = buildService();

        String r1 = service.message("es-ES", "", UUID.randomUUID().toString()).execute().body();
        String r2 = service.message("es-ES", "Java", UUID.randomUUID().toString()).execute().body();
        String r3 = service.message("en-GB", "", UUID.randomUUID().toString()).execute().body();
        String r4 = service.message("en-GB", "Java", UUID.randomUUID().toString()).execute().body();

        System.out.printf("Result: %s%n", r1);
        System.out.printf("Result: %s%n", r2);
        System.out.printf("Result: %s%n", r3);
        System.out.printf("Result: %s%n", r4);
    }

    private Service buildService() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(buildLoggingInterceptor())
                .eventListener(OkHttpMetricsEventListener.builder(registry, "okhttp.requests").build())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl("http://localhost:8080/").build();

        return retrofit.create(Service.class);
    }

    private Interceptor buildLoggingInterceptor() {
        return chain -> {
            Request request = chain.request();

            long t1 = System.nanoTime();
            System.out.println(String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            System.out.println(String.format("Received response for %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d, response.headers()));

            return response;
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }
}
