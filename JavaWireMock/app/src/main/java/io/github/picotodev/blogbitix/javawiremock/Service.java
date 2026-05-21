package io.github.picotodev.blogbitix.javawiremock;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Service {

    @GET("/message/{id}")
    Call<String> message(@Path("id") Long id);
}
