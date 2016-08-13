package io.github.picodotdev.blogbitix.datetimedatabase;

import java.sql.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Main {

    public static void main(String[] args) throws Exception {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost/user", "user", "user");

        {
            PreparedStatement create = connection.prepareStatement("CREATE TABLE IF NOT EXISTS date (id SERIAL, date TIMESTAMP, PRIMARY KEY(id))");
            create.execute();

            PreparedStatement delete = connection.prepareStatement("DELETE FROM date");
            delete.execute();
        }

        {
            PreparedStatement insert = connection.prepareStatement("INSERT INTO date (date) VALUES (?)");
            ZonedDateTime date = ZonedDateTime.of(2016, 10, 30, 2, 30, 0, 0, ZoneId.of("Europe/Madrid"));
            System.out.printf("Before database: %s\n", date.format(DateTimeFormatter.ISO_ZONED_DATE_TIME));

            insert.setTimestamp(1, Timestamp.valueOf(date.toLocalDateTime())); // !! timezone lost
            insert.execute();
        }

        {
            PreparedStatement select = connection.prepareStatement("SELECT date FROM date");
            ResultSet rs = select.executeQuery();
            rs.next();
            ZonedDateTime date = ZonedDateTime.ofInstant(rs.getTimestamp("date").toInstant(), ZoneId.of("Europe/Madrid"));
            System.out.printf("After database: %s\n", date.format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
        }
    }
}
