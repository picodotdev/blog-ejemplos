package io.github.picodotdev.keycloak.pages;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.tapestry5.annotations.Secure;

@Secure
@RequiresRoles("admin")
public class Admin {
}
