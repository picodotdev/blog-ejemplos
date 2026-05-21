package io.github.picodotdev.blogbitix.javapact;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ServiceClient implements Service {

    private Service service;

    public ServiceClient(OkHttpClient client, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(baseUrl).build();

        this.service = retrofit.create(Service.class);
    }

    @Override
    public Call<String> message(String acceptLanguage, String random) {
        return service.message(acceptLanguage, random);
    }

    @Override
    public Call<String> message(String name, String acceptLanguage, String random) {
        return service.message(name, acceptLanguage, random);
    }
}
