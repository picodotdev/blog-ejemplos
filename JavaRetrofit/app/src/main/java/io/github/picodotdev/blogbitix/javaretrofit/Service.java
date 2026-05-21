package io.github.picodotdev.blogbitix.javaretrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {

    @GET("/message/{name}")
    Call<String> message(@Header("Accept-Language") String acceptLanguage, @Path("name") String name, @Query("random") String random);
}
