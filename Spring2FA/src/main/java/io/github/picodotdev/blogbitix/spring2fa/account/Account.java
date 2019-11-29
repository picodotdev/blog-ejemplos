package io.github.picodotdev.blogbitix.spring2fa.account;

import java.util.List;

public class Account {

    private String username;
    private String password;

    private String secret;
    private Boolean auth2fa;

    private List<String> roles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Boolean isAuth2fa() {
        return auth2fa;
    }

    public void setAuth2fa(Boolean auth2fa) {
        this.auth2fa = auth2fa;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
