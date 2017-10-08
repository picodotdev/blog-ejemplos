package io.github.picodotdev.blogbitix.http;

import java.net.URI;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpClient.Redirect;
import jdk.incubator.http.HttpClient.Version;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;
import jdk.incubator.http.HttpResponse.BodyHandler;

public class Main {

    public static void main(String... args) throws Exception {
        HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.SECURE).version(HttpClient.Version.HTTP_2).build();

        HttpResponse<String> response = client.send(HttpRequest.newBuilder(new URI("https://www.google.es/")).headers("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:56.0) Gecko/20100101 Firefox/56.0").GET().build(), BodyHandler.asString());

        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Headers:");
        response.headers().map().entrySet().forEach(System.out::println);
        System.out.println("Body:");
        System.out.println(response.body());
    }
}

