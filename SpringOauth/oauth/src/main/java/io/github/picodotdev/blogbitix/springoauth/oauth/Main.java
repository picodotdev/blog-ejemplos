package io.github.picodotdev.blogbitix.springoauth.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableAuthorizationServer
public class Main {

	// curl -X POST -u "client:1234567890" -d "grant_type=client_credentials" "http://localhost:8095/oauth/token"
	// oauthServer.allowFormAuthenticationForClients();
	// curl -X POST "http://localhost:8095/oauth/token?grant_type=client_credentials&client_id=client&client_secret=1234567890"

	// curl -X POST -u "client:1234567890" -d "token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsic2VydmljZSJdLCJzY29wZSI6WyJyZWFkIl0sImV4cCI6MTU0OTY3NDA0NSwiYXV0aG9yaXRpZXMiOlsiQ0xJRU5UIl0sImp0aSI6IjNjYzk5ZGY5LWI3MDctNGZkZS04YTMwLTk2YjBhODA2N2ZkZSIsImNsaWVudF9pZCI6ImNsaWVudCJ9.KfPV17gqUbGbTOB8ko__7dESiB0t4cynnMvvT6pta28" http://localhost:8095/oauth/check_token
	// signing key clave pública y privada

	// curl -X GET -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsic2VydmljZSJdLCJzY29wZSI6WyJyZWFkIl0sImV4cCI6MTU0OTY2MjcwMSwiYXV0aG9yaXRpZXMiOlsiQ0xJRU5UIl0sImp0aSI6IjY2YWM3OGQ5LWU4YzAtNGMwYi1hMDEzLTMzYjhlOWQ5MWY0ZiIsImNsaWVudF9pZCI6ImNsaWVudCJ9.O18vhdU3-Cu1qJQGRJAaW3pfhBEd7YG-8eV6T6lOO08" http://localhost:8080/

	// http://websystique.com/spring-security/secure-spring-rest-api-using-oauth2/
	// https://www.devglan.com/spring-security/spring-boot-oauth2-jwt-example
	// https://www.jorgehernandezramirez.com/2017/04/17/spring-boot-oauth-server/
	// https://www.baeldung.com/spring-security-oauth-jwt
	// https://stackoverflow.com/questions/23950068/spring-oauth2-full-authentication-is-required-to-access-this-resource
	// https://stackoverflow.com/questions/52606720/issue-with-having-multiple-websecurityconfigureradapter-in-spring-boot
	// Errores
	// https://stackoverflow.com/questions/49654143/spring-security-5-there-is-no-passwordencoder-mapped-for-the-id-null

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}
