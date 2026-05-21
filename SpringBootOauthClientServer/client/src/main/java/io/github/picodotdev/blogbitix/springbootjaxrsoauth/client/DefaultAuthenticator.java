package io.github.picodotdev.blogbitix.springbootjaxrsoauth.client;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DefaultAuthenticator implements Authenticator {

    private AccessTokenRepository accessTokenRepository;

    public DefaultAuthenticator(AccessTokenRepository accessTokenRepository) {
        this.accessTokenRepository = accessTokenRepository;
    }

    @Nullable
    @Override
    public Request authenticate(@Nullable Route route, @NotNull Response response) throws IOException {
        if (!tryAuthenticate(response)) {
            return null;
        }

        synchronized (this) {
            if (accessTokenRepository.getAccessToken() == null) {
                accessTokenRepository.requestAccessToken();
            } else {
                accessTokenRepository.refreshAccessToken();
            }
        }

        return response.request().newBuilder()
                .header("Authorization", "Bearer " + accessTokenRepository.getAccessToken())
                .build();
    }

    private boolean tryAuthenticate(Response response) {
        return response.request().header("Authorization") == null || response.priorResponse() == null;
    }
}
