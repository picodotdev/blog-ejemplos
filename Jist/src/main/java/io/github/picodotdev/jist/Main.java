package io.github.picodotdev.jist;

import com.beust.jcommander.JCommander;

import javax.json.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class Main {

    private String user;
    private HttpClient client;

    private Main(String user) {
        this.user = user;
    }

    private void download() throws Exception {
        List<JsonObject> gists = getUserGists();
        downloadGists(gists);
    }

    private List<JsonObject> getUserGists() throws Exception {
        List<JsonObject> gists = new ArrayList<>();
        int page = 1;
        boolean more = true;

        do {
            List<JsonObject> array = getUserGists(page);
            gists.addAll(array);
            more = array.size() > 0;
            page += 1;
        } while(more);

        return gists;
    }

    private List<JsonObject> getUserGists(int page) throws Exception {
        String userGistsUrl = String.format("https://api.github.com/users/%s/gists?page=%s&per_page=100", user, page);
        HttpResponse<String> response = getClient().send(HttpRequest.newBuilder(new URI(userGistsUrl)).GET().build(), HttpResponse.BodyHandlers.ofString());
        JsonReader gistsJsonReader = Json.createReader(new StringReader(response.body()));
        JsonArray gistsArray = gistsJsonReader.readArray();
        return gistsArray.getValuesAs(JsonObject.class);
    }

    private void downloadGists(List<JsonObject> gists) throws Exception {
        for (JsonObject gist : gists) {
            String id = gist.getString("id");
            System.out.println(id);
            LocalDateTime creationDate = LocalDateTime.parse(gist.getString("created_at"), DateTimeFormatter.ISO_ZONED_DATE_TIME);
            JsonObject files = gist.getJsonObject("files");

            for (Map.Entry<String, JsonValue> file : files.entrySet()) {
                String fileName = file.getKey();
                String url = file.getValue().asJsonObject().getString("raw_url");
                downloadGistFile(creationDate, id, fileName, url);
            }
        }
    }

    private void downloadGistFile(LocalDateTime date, String id, String fileName, String url) throws Exception {
        String year = String.valueOf(date.getYear());
        Path path = Paths.get("gists", year, id, fileName);
        path.toFile().getParentFile().mkdirs();
        getClient().send(HttpRequest.newBuilder(new URI(url)).GET().build(), HttpResponse.BodyHandlers.ofFile(path));
    }

    private String getAuthorization(String user, String password) {
        return "Basic: " + Base64.getEncoder().encodeToString(String.format("%s:%s", user, password).getBytes());
    }

    private HttpClient getClient() {
        return HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).version(HttpClient.Version.HTTP_2).build();
    }

    private static Arguments parseArgs(String[] args) {
        Arguments arguments = new Arguments();

        JCommander.newBuilder()
                .addObject(arguments)
                .build()
                .parse(args);

        return arguments;
    }
    
    public static void main(String[] args) throws Exception {
        Arguments arguments = parseArgs(args);
        new Main(arguments.getUser()).download();
    }    
}
