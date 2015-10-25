package io.github.picodotdev.blogbitix.multidatabase.spring;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.jooq.ConnectionProvider;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.ResourceTransactionManager;

import io.github.picodotdev.blogbitix.multidatabase.service.InventoryService;
import io.github.picodotdev.blogbitix.multidatabase.service.InventoryServiceImpl;
import io.github.picodotdev.blogbitix.multidatabase.service.PurchasesService;
import io.github.picodotdev.blogbitix.multidatabase.service.PurchasesServiceImpl;

@Configuration
@ComponentScan({ "io.github.picodotdev.blogbitix.jooq" })
@EnableTransactionManagement
public class AppConfiguration {

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        // ds.setDriverClassName("org.postgresql.Driver");
        // ds.setUrl("jdbc:postgresql://localhost:5432/app");
        // ds.setUsername("sa");
        // ds.setPassword("sa");
        // ds.setDriverClassName("com.mysql.jdbc.Driver");
        // ds.setUrl("jdbc:mysql://localhost:3306/app");
        // ds.setUsername("root");
        // ds.setPassword("");
        ds.setDriverClassName("org.h2.Driver");
        ds.setUrl("jdbc:h2:./misc/database/app");
        ds.setUsername("sa");
        ds.setPassword("sa");
        return ds;
    }

    @Bean(name="inventoryTransactionManager")
    public ResourceTransactionManager inventoryTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
    
    @Bean(name="purchasesTransactionManager")
    public ResourceTransactionManager purchasesTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name="inventoryConnectionProvider")
    public ConnectionProvider inventoryConnectionProvider(DataSource dataSource) {
        return new DataSourceConnectionProvider(dataSource);
    }
    
    @Bean(name="purchasesConnectionProvider")
    public ConnectionProvider purchasesConnectionProvider(DataSource dataSource) {
        return new DataSourceConnectionProvider(dataSource);
    }

    @Bean(name="inventoryConfig")
    public org.jooq.Configuration inventoryConfig(@Qualifier("inventoryConnectionProvider") ConnectionProvider connectionProvider) {
        DefaultConfiguration config = new DefaultConfiguration();
        config.set(connectionProvider);
        // config.set(SQLDialect.POSTGRES_9_4);
        // config.set(SQLDialect.MYSQL);
        config.set(SQLDialect.H2);
        return config;
    }

    @Bean(name="purchasesConfig")
    public org.jooq.Configuration purchasesConfig(@Qualifier("purchasesConnectionProvider") ConnectionProvider connectionProvider) {
        DefaultConfiguration config = new DefaultConfiguration();
        config.set(connectionProvider);
        // config.set(SQLDialect.POSTGRES_9_4);
        // config.set(SQLDialect.MYSQL);
        config.set(SQLDialect.H2);
        return config;
    }

    @Bean(name="inventoryDSLContext")
    public DSLContext inventoryDSLContext(@Qualifier("inventoryConfig") org.jooq.Configuration config) {
        return DSL.using(config);
    }
    
    @Bean(name="purchasesDSLContext")
    public DSLContext purchasesDSLContext(@Qualifier("purchasesConfig") org.jooq.Configuration config) {
        return DSL.using(config);
    }

    @Bean
    public InventoryService inventoryService(@Qualifier("inventoryDSLContext") DSLContext context) {
        return new InventoryServiceImpl(context);
    }
    
    @Bean
    public PurchasesService purchasesService(@Qualifier("purchasesDSLContext") DSLContext context, InventoryService inventory) {
        return new PurchasesServiceImpl(context, inventory);
    }
}
