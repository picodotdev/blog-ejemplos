package io.github.picodotdev.blogbitix.spring2fa.account;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class InMemoryAccountRepository implements AccountRepository {

    private static String ADMIN_SECRET = "6YFX5TVT76OHHNMS";

    private List<Account> accounts;

    public InMemoryAccountRepository() {
        accounts = new ArrayList<Account>();
        init();
    }

    private void init() {
        Account admin = new Account();
        admin.setUsername("admin");
        admin.setPassword("{noop}password");
        admin.setAuth2fa(true);
        admin.setSecret(ADMIN_SECRET);
        admin.setRoles(Arrays.asList("ROLE_USER"));

        Account user = new Account();
        user.setUsername("user");
        user.setPassword("{noop}password");
        user.setAuth2fa(false);
        user.setRoles(Arrays.asList("ROLE_USER"));

        accounts.add(admin);
        accounts.add(user);
    }

    @Override
    public Account find(String username) {
        return accounts.stream().filter(account -> account.getUsername().equals(username)).findFirst().orElse(null);
    }
}
