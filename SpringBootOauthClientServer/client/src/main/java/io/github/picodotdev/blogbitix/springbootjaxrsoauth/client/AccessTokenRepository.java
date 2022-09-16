package io.github.picodotdev.blogbitix.springbootjaxrsoauth.client;

import java.io.IOException;
import javax.json.Json;
import javax.json.JsonObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AccessTokenRepository {

    private String tokenEndpoint;
    private OkHttpClient client;
    private String clientId;
    private String clientSecret;

    private String accessToken;
    private String refreshToken;

    public AccessTokenRepository(String tokenEndpoint, OkHttpClient client, String clientId, String clientSecret) {
        this.tokenEndpoint = tokenEndpoint;
        this.client = client;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void requestAccessToken() throws IOException {
        System.out.println("Requesting access token");

        RequestBody data = new FormBody.Builder()
                .add("grant_type", "client_credentials")
                .add("client_id", clientId)
                .add("client_secret", clientSecret)
                .build();
        Request tokenRequest = new Request.Builder().url(tokenEndpoint).post(data).build();
        Response tokenResponse = client.newCall(tokenRequest).execute();

        try (tokenResponse) {
            JsonObject tokenObject = Json.createReader(tokenResponse.body().charStream()).readObject();
            accessToken = tokenObject.getString("access_token");
            refreshToken = tokenObject.getString("refresh_token", null);
            Integer expiresIn = tokenObject.getInt("expires_in");
            Integer refreshExpires = tokenObject.getInt("refresh_expires_in", -1);

            System.out.printf("Access token: %s%n", accessToken);
            System.out.printf("Expires in: %d%n", expiresIn);
            System.out.printf("Refresh expires in: %d%n", refreshExpires);
            System.out.printf("Refresh token: %s%n", refreshToken);
        }
    }

    public void refreshAccessToken() throws IOException {
        System.out.println("Refreshing access token");

        RequestBody data = new FormBody.Builder()
                .add("grant_type", "refresh_token")
                .add("client_id", clientId)
                .add("client_secret", clientSecret)
                .add("refresh_token", refreshToken)
                .build();
        Request tokenRequest = new Request.Builder().url(tokenEndpoint).post(data).build();
        Response tokenResponse = client.newCall(tokenRequest).execute();
        try (tokenResponse) {
            JsonObject tokenObject = Json.createReader(tokenResponse.body().charStream()).readObject();
            accessToken = tokenObject.getString("access_token");
            refreshToken = tokenObject.getString("refresh_token", null);
            Integer expiresIn = tokenObject.getInt("expires_in");
            Integer refreshExpires = tokenObject.getInt("refresh_expires_in", -1);

            System.out.printf("Access token: %s%n", accessToken);
            System.out.printf("Expires in: %d%n", expiresIn);
            System.out.printf("Refresh expires in: %d%n", refreshExpires);
            System.out.printf("Refresh token: %s%n", refreshToken);
        }
    }
}
