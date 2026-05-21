package io.github.picodotdev.blogbitix.javapact;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {

    @GET("/message")
    Call<String> message(@Header("Accept-Language") String acceptLanguage, @Query("random") String random);

    @GET("/message/{name}")
    Call<String> message(@Path("name") String name, @Header("Accept-Language") String acceptLanguage, @Query("random") String random);
}
