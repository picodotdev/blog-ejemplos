package io.github.picodotdev.gradle.web.services.spring;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.jooq.ConnectionProvider;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.ResourceTransactionManager;

import io.github.picodotdev.gradle.core.services.ItemDAO;
import io.github.picodotdev.gradle.core.services.ItemDAOImpl;

@Configuration
@ComponentScan({ "io.github.picodotdev.gradle" })
@EnableTransactionManagement
public class AppConfiguration {

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl("jdbc:postgresql://localhost:5432/app");
        ds.setUsername("sa");
        ds.setPassword("sa");
        return ds;
    }

    @Bean
    public DataSource transactionAwareDataSource(DataSource dataSource) {
        return new TransactionAwareDataSourceProxy(dataSource);
    }

    @Bean
    public ResourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public ConnectionProvider connectionProvider(DataSource dataSource) {
        return new DataSourceConnectionProvider(dataSource);
    }

    @Bean
    public org.jooq.Configuration config(ConnectionProvider connectionProvider) {
        DefaultConfiguration config = new DefaultConfiguration();
        config.set(connectionProvider);
        config.set(SQLDialect.H2);
        return config;
    }

    @Bean
    public DSLContext dsl(org.jooq.Configuration config) {
        return DSL.using(config);
    }

    @Bean
    public ItemDAO itemDAO(DSLContext context) {
        return new ItemDAOImpl(context);
    }
}
