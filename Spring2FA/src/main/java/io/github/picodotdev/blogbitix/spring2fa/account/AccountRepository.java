package io.github.picodotdev.blogbitix.spring2fa.account;

public interface AccountRepository {

    Account find(String username);
}
