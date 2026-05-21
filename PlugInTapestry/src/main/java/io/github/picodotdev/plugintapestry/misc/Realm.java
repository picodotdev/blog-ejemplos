package io.github.picodotdev.plugintapestry.misc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;

public class Realm extends AuthorizingRealm {

    private static Map<String, Map<String, Object>> users;
    private static Map<String, Set<String>> permissions;
    
    // Para hacer más costoso el cálculo del hash y dificultar un ataque de fuerza bruta
    private static final int HASH_ITERATIONS = 5_000_000;

    static {
        // Generar una contraseña de clave «password», con SHA-512 y con «salt» aleatorio.
        ByteSource saltSource = new SecureRandomNumberGenerator().nextBytes();
        byte[] salt = saltSource.getBytes();
        Sha512Hash hash = new Sha512Hash("password", saltSource, HASH_ITERATIONS);
        String password = hash.toHex();
        // Contraseña codificada en Base64
        //String password = hash.toBase64();

        // Permissions (role, permissions)
        permissions = new HashMap<>();
        permissions.put("root", new HashSet<>(Arrays.asList(new String[] { "cuenta:reset" })));
        
        // Roles
        Set<String> roles = new HashSet<>();
        roles.add("root");

        // Usuario (property, value)
        Map<String, Object> user = new HashMap<>();
        user.put("username", "root");
        user.put("password", password);
        user.put("salt", salt);
        user.put("locked", Boolean.FALSE);
        user.put("expired", Boolean.FALSE);
        user.put("roles", roles);

        // Usuarios
        users = new HashMap<>();
        users.put("root", user);
    }

	public Realm() {
		super(new MemoryConstrainedCacheManager());
		
		HashedCredentialsMatcher cm = new HashedCredentialsMatcher(Sha512Hash.ALGORITHM_NAME);
		cm.setHashIterations(HASH_ITERATIONS);
		//cm.setStoredCredentialsHexEncoded(false);
		
		setName("local");
		setAuthenticationTokenClass(UsernamePasswordToken.class);
		setCredentialsMatcher(cm);
	}

    /**
     * Proporciona la autenticación de los usuarios.
     */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken atoken = (UsernamePasswordToken) token;

		String username = atoken.getUsername();

		if (username == null) { throw new AccountException("Null usernames are not allowed by this realm."); }

		Map<String, Object> user = findByUsername(username);
		if (user == null) {
		    return null;
        }

        String password = (String) user.get("password");
        byte[] salt = (byte []) user.get("salt");
        boolean locked = (boolean) user.get("locked");
        boolean expired = (boolean) user.get("expired");

		if (locked) { throw new LockedAccountException("Account [" + username + "] is locked."); }
		if (expired) { throw new ExpiredCredentialsException("The credentials for account [" + username + "] are expired"); }

		return new SimpleAuthenticationInfo(username, password, new SimpleByteSource(salt), getName());
	}

    /**
     * Proporciona la autorización de los usuarios.
     */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		if (principals == null) { throw new AuthorizationException("PrincipalCollection was null, which should not happen"); }

		if (principals.isEmpty()) { return null; }

		if (principals.fromRealm(getName()).size() <= 0) { return null; }

		// Obtener el usuario
		String username = (String) principals.fromRealm(getName()).iterator().next();
		if (username == null) { return null; }
		Map<String, Object> user = findByUsername(username);
		if (user == null) { return null; }
		
		// Obtener los roles
        Set<String> roles = (Set<String>) user.get("roles");
        
        // Obtener los permisos de los roles
        Set<String> p = new HashSet<>();
        for (String role : roles) {
        	p.addAll((Set<String>) permissions.get(role));
        } 

        // Devolver el objeto de autorización
        SimpleAuthorizationInfo ai = new SimpleAuthorizationInfo();
        ai.setRoles(roles);
        ai.setStringPermissions(p);
		return ai;
	}

	private Map<String, Object> findByUsername(String username) {
        return users.get(username);
	}
}
