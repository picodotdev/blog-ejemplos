package io.github.picodotdev.blogbitix.springbootjaxrsoauth;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;

public class ClientMain {

    public static void main(String[] args) throws Exception {
        CloseableHttpClient client = HttpClients.custom()
                .setSSLSocketFactory(new SSLConnectionSocketFactory(SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build(), NoopHostnameVerifier.INSTANCE))
                .build();

        //curl -i http://localhost:9080/auth/realms/springbootjaxrs/.well-known/openid-configuration
        System.out.println("Getting configuration...");
        HttpGet configurationRequest = new HttpGet("http://localhost:9080/auth/realms/springbootjaxrs/.well-known/openid-configuration");
        CloseableHttpResponse configurationResponse = client.execute(configurationRequest);

        JsonObject configurarionObject = Json.createReader(configurationResponse.getEntity().getContent()).readObject();
        String tokenEndpoint = configurarionObject.getString("token_endpoint");
        configurationResponse.close();

        //curl -i http://localhost:9080/auth/realms/springbootjaxrs/protocol/openid-connect/token -d "grant_type=client_credentials&client_id=client&client_secret=06212c72-8734-4cd7-898d-7c4afb17e0a2"
        System.out.println("Getting an access token...");
        HttpPost tokenRequest = new HttpPost(tokenEndpoint);
        List<NameValuePair> data = new ArrayList<NameValuePair>();
        data.add(new BasicNameValuePair("grant_type", "client_credentials"));
        data.add(new BasicNameValuePair("client_id", "client"));
        data.add(new BasicNameValuePair("client_secret", "06212c72-8734-4cd7-898d-7c4afb17e0a2"));
        tokenRequest.setEntity(new UrlEncodedFormEntity(data));
        CloseableHttpResponse tokenResponse = client.execute(tokenRequest);

        JsonObject tokenObject = Json.createReader(tokenResponse.getEntity().getContent()).readObject();
        String accessToken = tokenObject.getString("access_token");
        Integer expiresIn = tokenObject.getInt("expires_in");
        Integer refreshExpires = tokenObject.getInt("refresh_expires_in");
        String refreshToken = tokenObject.getString("refresh_token");

        System.out.printf("Access token: %s%n", accessToken);
        System.out.printf("Expires in: %d%n", expiresIn);
        System.out.printf("Refresh expires in: %d%n", refreshExpires);
        System.out.printf("Refresh token: %s%n", refreshToken);

        //curl -ik -H "Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJMTl9oRC1CX0FFSlBoS1VsM3ZjOTFoUFBrRkxhSE84clU5UW1CaGVHWW4wIn0.eyJqdGkiOiI3YmQ3NjE0Mi0wYTJiLTRiMzYtOWE5Ny0zYWNmNGI5YzFkYjkiLCJleHAiOjE0NzE2NDI4OTksIm5iZiI6MCwiaWF0IjoxNDcxNjQyNTk5LCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjkwODAvYXV0aC9yZWFsbXMva2V5Y2xvYWsiLCJhdWQiOiJhcGkiLCJzdWIiOiI5ZGZmMTJmMS1jOWYyLTRmZTgtODI2Mi1kZDlmMWJjM2E1ZjgiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJhcGkiLCJhdXRoX3RpbWUiOjAsInNlc3Npb25fc3RhdGUiOiIwZTFjNjM5OC0wNmQ1LTRjMDItOGIzZC04NDVmMDc4ZjBiNTYiLCJhY3IiOiIxIiwiY2xpZW50X3Nlc3Npb24iOiI0ZjViNjk5OS02YmU3LTQzZmUtYjZiMy04MTYzYWM4NjYyYmUiLCJhbGxvd2VkLW9yaWdpbnMiOltdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiYXBpIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYXBpIjp7InJvbGVzIjpbImFwaSJdfSwiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsInZpZXctcHJvZmlsZSJdfX0sImNsaWVudElkIjoiYXBpIiwiY2xpZW50SG9zdCI6IjE3Mi4xNy4wLjEiLCJuYW1lIjoiIiwicHJlZmVycmVkX3VzZXJuYW1lIjoic2VydmljZS1hY2NvdW50LWFwaSIsImNsaWVudEFkZHJlc3MiOiIxNzIuMTcuMC4xIiwiZW1haWwiOiJzZXJ2aWNlLWFjY291bnQtYXBpQHBsYWNlaG9sZGVyLm9yZyJ9.gFe_Mphm-UjzpMRYOWbiS32Va3Lo9eeTb6sN5qHHGR-ZvYeSthuzZZScdOiagh-PJ4WDmQL9nK677VWYr6wP7CW08SeC9dJ5idbuimONvHB_3XxFHHB71eM6bIoWSpJb5hfLLnNsEx5Ko4D3YBi6YSzt1U8M07SW1ve35pldhYrM8f81l6DRiKxZJxScZfbIhp0yMzOWyDhcR9YTrbJ2R9zx8SGoaKMxZZssMWmylGt5BSrDWJbMLUFCbGdqarBiYIWYR08s99efHj4mkVGOM95upNDstvpmnRFohsckVjcylZ5bkapzSfzc_LUtMrYethgi76oVufeMZ7HooWsvdA" https://localhost:8443/api/message?message=Hola
        System.out.println("Calling oauth secured service...");
        HttpGet serviceRequest = new HttpGet("https://localhost:8443/api/message?message=Hola");
        serviceRequest.addHeader("Authorization", "Bearer " + accessToken);
        CloseableHttpResponse serviceResponse = client.execute(serviceRequest);

        JsonObject serviceObject = Json.createReader(serviceResponse.getEntity().getContent()).readObject();

        System.out.printf("Result: %s%n", serviceObject.toString());
    }
}
