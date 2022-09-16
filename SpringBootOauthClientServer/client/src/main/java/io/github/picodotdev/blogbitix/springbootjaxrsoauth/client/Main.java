package io.github.picodotdev.blogbitix.springbootjaxrsoauth.client;

import javax.json.Json;
import javax.json.JsonObject;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Main {

    public static void main(String[] args) throws Exception {
        OkHttpClient baseClient = new OkHttpClient.Builder()
                .build();

        //curl -i http://localhost:9080/realms/my-realm/.well-known/openid-configuration
        System.out.println("Getting configuration...");
        Request configurationRequest = new Request.Builder().url("http://localhost:9080/realms/my-realm/.well-known/openid-configuration").get().build();
        Response configurationResponse = baseClient.newCall(configurationRequest).execute();

        JsonObject configurationObject = Json.createReader(configurationResponse.body().charStream()).readObject();
        String tokenEndpoint = configurationObject.getString("token_endpoint");

        AccessTokenRepository accessTokenRepository = new AccessTokenRepository(tokenEndpoint, baseClient, "spring-boot-client", "Bg1r6mOYsFraDw7u8VCgmGl4JtK8vShX");

        OkHttpClient client = baseClient.newBuilder()
                .authenticator(new DefaultAuthenticator(accessTokenRepository))
                .build();

        System.out.println("Getting an access token...");
        accessTokenRepository.requestAccessToken();

        //curl -ik -H "Authorization: Bearer aaaaa.bbbbb.ccccc" http://localhost:9080/message?string=Hola
        while(true) {
            System.out.println("Calling OAuth secured service...");
            Request serviceRequest = new Request.Builder()
                    .url(HttpUrl.parse("http://localhost:8080/message").newBuilder().addQueryParameter("string", "Hola").build())
                    .header("Authorization", "Bearer " + accessTokenRepository.getAccessToken())
                    .get()
                    .build();
            Response serviceResponse = client.newCall(serviceRequest).execute();

            try (serviceResponse) {
                JsonObject serviceObject = Json.createReader(serviceResponse.body().charStream()).readObject();
                System.out.printf("Result: %s%n", serviceObject.toString());
            }

            Thread.sleep(5000);
        }
    }
}
