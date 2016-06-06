package io.github.picodotdev.blogbitix.javageolocation;

import java.io.InputStream;
import java.net.InetAddress;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;
import com.maxmind.geoip2.record.Location;
import com.maxmind.geoip2.record.Postal;
import com.maxmind.geoip2.record.Subdivision;

public class Main {

    public static void main(String[] args) throws Exception {
        // Acceder a la base de datos
        InputStream database = Main.class.getResourceAsStream("/GeoLite2-City.mmdb");
        DatabaseReader reader = new DatabaseReader.Builder(database).build();

        InetAddress ip = InetAddress.getByName("85.84.77.93");

        // Hacer la búsqueda
        CityResponse response = reader.city(ip);

        // País
        Country country = response.getCountry();
        System.out.printf("ISO Code: %s%n", country.getIsoCode());
        System.out.printf("Country: %s%n", country.getName());
        System.out.printf("Country (zh-CN): %s%n", country.getNames().get("zh-CN"));

        Subdivision subdivision = response.getMostSpecificSubdivision();
        System.out.printf("Subdivision Name: %s%n", subdivision.getName());
        System.out.printf("Subdivision ISO Code: %s%n", subdivision.getIsoCode());

        // Ciudad
        City city = response.getCity();
        System.out.printf("City: %s%n", city.getName());

        // Código postal
        Postal postal = response.getPostal();
        System.out.printf("Postal Code: %s%n", postal.getCode());

        // Geolocalizacion
        Location location = response.getLocation();
        System.out.printf("Latitude: %s%n", location.getLatitude());
        System.out.printf("Longitude: %s%n", location.getLongitude());
    }
}
