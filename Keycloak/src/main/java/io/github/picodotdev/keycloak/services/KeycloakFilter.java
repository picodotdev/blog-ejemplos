package io.github.picodotdev.keycloak.services;

import org.apache.shiro.SecurityUtils;
import org.apache.tapestry5.services.HttpServletRequestFilter;
import org.apache.tapestry5.services.HttpServletRequestHandler;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.representations.AccessToken;
import org.tynamo.security.federatedaccounts.oauth.tokens.OauthAccessToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class KeycloakFilter implements HttpServletRequestFilter {

    @Override
    public boolean service(HttpServletRequest request, HttpServletResponse response, HttpServletRequestHandler handler) throws IOException {
        AccessToken accessToken = getAccessToken(request);
        if (accessToken != null) {
            SecurityUtils.getSubject().login(new OauthAccessToken(accessToken, accessToken.getExpiration()));
        }
        return handler.service(request, response);
    }

    private AccessToken getAccessToken(HttpServletRequest request) {
        if (!(request.getUserPrincipal() instanceof KeycloakPrincipal)) {
            return null;
        }
        KeycloakPrincipal principal = (KeycloakPrincipal) request.getUserPrincipal();
        return principal.getKeycloakSecurityContext().getToken();
    }
}
