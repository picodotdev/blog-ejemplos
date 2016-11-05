package io.github.picodotdev.blogbitix.holamundojavers;

import org.javers.common.collections.Sets;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Change;
import org.javers.core.diff.Diff;
import org.javers.repository.jql.QueryBuilder;
import org.javers.repository.sql.ConnectionProvider;
import org.javers.repository.sql.DialectName;
import org.javers.repository.sql.JaversSqlRepository;
import org.javers.repository.sql.SqlRepositoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@SpringBootApplication
public class Main implements CommandLineRunner {

	@Bean
	public ConnectionProvider connectionProvider() {
		return new ConnectionProvider() {
			@Override
			public Connection getConnection() throws SQLException {
                try {
                    Class.forName("org.postgresql.Driver");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    return null;
                }

                Properties props = new Properties();
                props.setProperty("user","admin");
                props.setProperty("password","admin");
                return DriverManager.getConnection("jdbc:postgresql:javers", props);
			}
		};
	}

	@Bean
	public Javers javers(ConnectionProvider connectionProvider) {
		JaversSqlRepository sqlRepository = SqlRepositoryBuilder
				.sqlRepository()
				.withConnectionProvider(connectionProvider)
				.withDialect(DialectName.POSTGRES).build();
		return JaversBuilder.javers().registerJaversRepository(sqlRepository).build();
	}

	@Autowired
	private Javers javers;

	@Override
	public void run(String... args) {
		Category drink = new Category("Drink");
        Category sport = new Category("Sport");
		Category fruit = new Category("Fruit");

		Product aquarius1 = new Product("Aquarius", new BigDecimal("1.75"), Collections.singleton(drink));
		Product aquarius2 = new Product("Aquarius", new BigDecimal("0.90"), Collections.singleton(sport));

		// Diff
		System.out.println("Diff...");
		Diff diff1 = javers.compare(aquarius1, aquarius2);
		System.out.println(diff1);

		// Commit
		System.out.println("Commit...");
		javers.commit("author", aquarius1);

		aquarius1.setPrice(new BigDecimal("2.00"));
		javers.commit("author", aquarius1);

		aquarius1.setPrice(new BigDecimal("1.60"));
        aquarius1.setCategories(Sets.asSet(drink, sport));
		javers.commit("author", aquarius1);

		// JQL
		System.out.println("Query...");
		List<Change> changes = javers.findChanges(QueryBuilder.byInstanceId("Aquarius", Product.class).andProperty("price").build());
		changes.stream().forEach(change -> {
			System.out.println(change);
		});
	}

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}
