@Grab(group='javax.activation', module='activation', version='1.1.1') 
@Grab(group='javax', module='javaee-api', version='7.0')
@Grab(group='org.glassfish.jersey.core', module='jersey-client', version='2.25.1')

import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.Entity
import javax.ws.rs.core.Response
import java.security.KeyStore

import org.glassfish.jersey.SslConfigurator

class Main {

    void get() {
        Response response = buildSslClient()
                .target("https://localhost").path("/")
                .request()
                .header("Accept", "text/html")
                .get()

        println(response.getStatus())
        println(response.readEntity(String.class))
    }

    private static Client buildSslClient() {
        return ClientBuilder.newBuilder().sslContext(buildSslContext()).build()
    }

    private static SSLContext buildSslContext() {
        return SslConfigurator.newInstance()
                .trustStoreFile("ca.jks")
                //.trustStoreFile("ca-unknown.jks")
                .trustStorePassword("password")
                .keyStoreFile("client.jks")
                //.keyStoreFile("client-unknown.jks")
                .keyPassword("password")
                .createSSLContext();
    }
}

new Main().get()
