package io.github.picodotdev.keycloak.services;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.keycloak.representations.AccessToken;
import org.tynamo.security.federatedaccounts.oauth.tokens.OauthAccessToken;

public class AppRealm extends AuthorizingRealm {

    public AppRealm() {
        super(new MemoryConstrainedCacheManager());

        setAuthenticationTokenClass(OauthAccessToken.class);
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        AccessToken accessToken = (AccessToken) authenticationToken.getPrincipal();
        return new SimpleAuthenticationInfo(accessToken, accessToken, accessToken.getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        AccessToken accessToken = (AccessToken) principalCollection.getPrimaryPrincipal();
        return new SimpleAuthorizationInfo(accessToken.getRealmAccess().getRoles());
    }
}
