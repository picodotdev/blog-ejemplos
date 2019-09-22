package io.github.picodotdev.blogbitix.http;

import java.net.URI;

import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class Main {

    public static void main(String... args) throws Exception {
        HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

        HttpResponse<String> response = client.send(HttpRequest.newBuilder(new URI("https://www.google.es/")).headers("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:56.0) Gecko/20100101 Firefox/56.0").GET().build(), HttpResponse.BodyHandlers.ofString());

        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Headers:");
        response.headers().map().entrySet().forEach(System.out::println);
        System.out.println("Body:");
        System.out.println(response.body());
    }
}

