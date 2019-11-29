package io.github.picodotdev.blogbitix.spring2fa;

public interface AccountRepository {

    Account find(String username);
}
