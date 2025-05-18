package io.github.picodotdev.blogbitix;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Response;

import io.github.picodotdev.catalog.client.api.CatalogClient;
import io.github.picodotdev.catalog.client.invoker.ApiClient;
import io.github.picodotdev.catalog.client.invoker.auth.HttpBearerAuth;
import io.github.picodotdev.catalog.client.model.ClientEvent;

public class Main {

    private static final int CALL_TIMEOUT = 15;
    private static final String BEARER_SCHEME = "Bearer";

    public static void main(String[] args) {
        OkHttpClient okhttp = new OkHttpClient.Builder().callTimeout(CALL_TIMEOUT, TimeUnit.SECONDS).build();

        ApiClient apiClient = new ApiClient();
        apiClient.getAdapterBuilder().baseUrl("https://picodotdev.github.io/catalog/");
        apiClient.setApiAuthorizations(Map.of(HttpBearerAuth.class.getSimpleName(), new HttpBearerAuth(BEARER_SCHEME)));
        apiClient.configureFromOkclient(okhttp);
        apiClient.getOkBuilder().addInterceptor(buildClientInterceptor("xxx"));
        CatalogClient catalogClient = apiClient.createService(CatalogClient.class);

        ClientEvent event = Main.execute(catalogClient.getEvent(1L));

        System.out.println(event.getName());
    }

    private static Interceptor buildClientInterceptor(String token) {
        return chain -> {
            Request.Builder builder = chain.request().newBuilder();
            builder.header("Accept", "application/json");
            builder.header("Content-Type", "application/json");
            builder.header("Authorization", "Bearer " + token);
            return chain.proceed(builder.build());
        };
    }

    public static <T> T execute(Call<T> call) {
        Predicate<Response<T>> success = (it) -> it.isSuccessful();
        Consumer<Response<T>> successHandler = (response) -> {
            throw new RuntimeException(String.valueOf(response.code()));
        };
        Consumer<Exception> exceptionHandler = (exception) -> {
            throw new RuntimeException(exception);
        };
        return execute(call, success, successHandler, exceptionHandler);
    }

    public static <T> T execute(Call<T> call, Predicate<Response<T>> success, Consumer<Response<T>> successHandler, Consumer<Exception> exceptionHandler) {
        Response<T> response = executeResponse(call, exceptionHandler);
        if (!success.test(response)) {
            successHandler.accept(response);
        }
        return response.body();
    }

    private static <T> Response<T> executeResponse(Call<T> call, Consumer<Exception> exceptionHandler) {
        try {
            return call.execute();
        } catch (IOException e) {
            exceptionHandler.accept(e);
            return null;
        }
    }
}