package io.github.picodotdev.keycloak.pages;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.tapestry5.annotations.Secure;

@Secure
@RequiresUser
public class User {
}
