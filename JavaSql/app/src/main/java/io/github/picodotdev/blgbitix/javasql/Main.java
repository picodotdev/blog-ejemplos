package io.github.picodotdev.blgbitix.javasql;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Locale;

public class Main {

    public static void main(String[] args) {
        DriverManager.drivers().forEach(d -> {
            System.out.printf("Driver: %s%n", d.getClass().getName());
        });

        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:database", "sa", "")) {
            connection.setAutoCommit(false);

            {
                Statement statement = connection.createStatement();
                statement.execute("CREATE TABLE product(id INT IDENTITY NOT NULL PRIMARY KEY, name VARCHAR(255), price DECIMAL(20, 2))");
            }

            {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO product (name, price) values (?, ?)", new String[] { "id" });

                preparedStatement.setString(1, "PlayStation 5");
                preparedStatement.setBigDecimal(2, new BigDecimal("499.99"));
                preparedStatement.executeUpdate();

                ResultSet resultSet1 = preparedStatement.getGeneratedKeys();
                while (resultSet1.next()) {
                    System.out.printf("Primary key: %s%n", resultSet1.getLong(1));
                }
                resultSet1.close();

                preparedStatement.setString(1, "Xbox Series X");
                preparedStatement.setBigDecimal(2, new BigDecimal("499.99"));
                preparedStatement.executeUpdate();

                ResultSet resultSet2 = preparedStatement.getGeneratedKeys();
                while (resultSet2.next()) {
                    System.out.printf("Primary key: %s%n", resultSet2.getLong(1));
                }
                resultSet2.close();

                connection.commit();
            }

            {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name, price FROM product");

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    System.out.printf("Product (id: %s, name: %s, price: %s)%n", resultSet.getLong(1), resultSet.getString(2), DecimalFormat.getCurrencyInstance(new Locale("es", "ES")).format(resultSet.getBigDecimal(3)));
                }
                resultSet.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
