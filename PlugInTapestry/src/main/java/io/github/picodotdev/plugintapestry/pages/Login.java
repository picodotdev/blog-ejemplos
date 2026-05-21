package io.github.picodotdev.plugintapestry.pages;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.Secure;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.tynamo.security.services.SecurityService;

/**
 * @tapestrydoc
 */
@Secure
public class Login {

	@Property
	private String usuario;

	@Property
	private String password;

	@Inject
	private SecurityService securityService;

	@Component
	private Form form;

	Object onActivate() {
		// Si el usuario ya está autenticado redirigir a la página Index
		if (securityService.isUser()) {
			return Index.class;
		}
		return null;
	}

	Object onValidateFromForm() {
		if (form.getHasErrors()) {
			return null;
		}

		Subject subject = securityService.getSubject();

		if (subject == null) {
			return null;
		}

		// Recolectar en el token los datos introducidos por el usuario
		UsernamePasswordToken token = new UsernamePasswordToken(usuario, password);
		token.setRememberMe(true);

		try {
			// Validar e iniciar las creadenciales del usuario
			subject.login(token);
		} catch (UnknownAccountException e) {
			form.recordError("Cuenta de usuario desconocida");
			return null;
		} catch (IncorrectCredentialsException e) {
			form.recordError("Credenciales inválidas");
			return null;
		} catch (LockedAccountException e) {
			form.recordError("Cuenta bloqueada");
			return null;
		} catch (AuthenticationException e) {
			form.recordError("Se ha producido un error");
			return null;
		}

		// Usuario autenticado, redirigir a la página Index
		return Index.class;
	}
}
