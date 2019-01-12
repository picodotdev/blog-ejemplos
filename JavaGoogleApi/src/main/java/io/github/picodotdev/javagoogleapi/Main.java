package io.github.picodotdev.javagoogleapi;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

public class Main {

    private static final String APPLICATION_NAME = "JavaGoogleApi";
    private static final String API_KEY = "AIzaSyDGwW...";
    private static final String CREDENTIALS_FILE_PATH = "/blogbitix-119471bc8ebf.json";
    private static final String SPREADSHEET_ID = "1JhBPGW4FN...";
    private static final String RANGE = "Hoja1";
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);

    private static Credential getCredentialsApiKey() {
        return new GoogleCredential().createScoped(SCOPES);
    }

    private static Credential getCredentialsServiceAccount(NetHttpTransport httpTransport, JsonFactory jsonFactory) throws IOException {
        InputStream in = Main.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        return GoogleCredential.fromStream(in, httpTransport, jsonFactory).createScoped(SCOPES);
    }

    public static void main(String... args) throws IOException, GeneralSecurityException {
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        Sheets service = new Sheets.Builder(httpTransport, jsonFactory, getCredentialsApiKey()).setApplicationName(APPLICATION_NAME).build();
        ValueRange response = service.spreadsheets().values().get(SPREADSHEET_ID, RANGE).setKey(API_KEY).execute();
        List<List<Object>> values = response.getValues();

        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
            return;
        }

        for (List<Object> row : values) {
            System.out.println(row.stream().map(Object::toString).collect(java.util.stream.Collectors.joining(", ")));
        }
    }
}