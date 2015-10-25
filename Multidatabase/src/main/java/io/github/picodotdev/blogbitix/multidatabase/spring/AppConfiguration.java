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
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.ResourceTransactionManager;

import io.github.picodotdev.blogbitix.multidatabase.service.InventoryService;
import io.github.picodotdev.blogbitix.multidatabase.service.DefaultInventoryService;
import io.github.picodotdev.blogbitix.multidatabase.service.PurchasesService;
import io.github.picodotdev.blogbitix.multidatabase.service.DefaultPurchasesService;

@Configuration
@ComponentScan({ "io.github.picodotdev.blogbitix.jooq" })
@EnableTransactionManagement
public class AppConfiguration {

    public static final String INVENTORY_TXM = "inventoryTransactionManager";
    public static final String PURCHASES_TXM = "purchasesTransactionManager";

    @Bean(name = "dataSource", destroyMethod = "close")
    @Primary
    @ConfigurationProperties(prefix = "datasource.primary")
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

    @Bean(name = INVENTORY_TXM)
    public ResourceTransactionManager inventoryTransactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = PURCHASES_TXM)
    public ResourceTransactionManager purchasesTransactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "inventoryTransactionAwareDataSourceProxy")
    public TransactionAwareDataSourceProxy inventoryTransactionAwareDataSourceProxy(@Qualifier("dataSource") DataSource dataSource) {
        return new TransactionAwareDataSourceProxy(dataSource);
    }

    @Bean(name = "purchasesTransactionAwareDataSourceProxy")
    public TransactionAwareDataSourceProxy purchasesTransactionAwareDataSourceProxy(@Qualifier("dataSource") DataSource dataSource) {
        return new TransactionAwareDataSourceProxy(dataSource);
    }

    @Bean(name = "inventoryConnectionProvider")
    public ConnectionProvider inventoryConnectionProvider(
            @Qualifier("inventoryTransactionAwareDataSourceProxy") TransactionAwareDataSourceProxy transactionAwareDataSourceProxy) {
        return new DataSourceConnectionProvider(transactionAwareDataSourceProxy);
    }

    @Bean(name = "purchasesConnectionProvider")
    public ConnectionProvider purchasesConnectionProvider(
            @Qualifier("purchasesTransactionAwareDataSourceProxy") TransactionAwareDataSourceProxy transactionAwareDataSourceProxy) {
        return new DataSourceConnectionProvider(transactionAwareDataSourceProxy);
    }

    @Bean(name = "inventoryConfig")
    public org.jooq.Configuration inventoryConfig(@Qualifier("inventoryConnectionProvider") ConnectionProvider connectionProvider) {
        DefaultConfiguration config = new DefaultConfiguration();
        config.set(connectionProvider);
        // config.set(SQLDialect.POSTGRES_9_4);
        // config.set(SQLDialect.MYSQL);
        config.set(SQLDialect.H2);
        return config;
    }

    @Bean(name = "purchasesConfig")
    public org.jooq.Configuration purchasesConfig(@Qualifier("purchasesConnectionProvider") ConnectionProvider connectionProvider) {
        DefaultConfiguration config = new DefaultConfiguration();
        config.set(connectionProvider);
        // config.set(SQLDialect.POSTGRES_9_4);
        // config.set(SQLDialect.MYSQL);
        config.set(SQLDialect.H2);
        return config;
    }

    @Bean(name = "inventoryDSLContext")
    public DSLContext inventoryDSLContext(@Qualifier("inventoryConfig") org.jooq.Configuration config) {
        return DSL.using(config);
    }

    @Bean(name = "purchasesDSLContext")
    public DSLContext purchasesDSLContext(@Qualifier("purchasesConfig") org.jooq.Configuration config) {
        return DSL.using(config);
    }

    @Bean
    public InventoryService inventoryService(@Qualifier("inventoryDSLContext") DSLContext context) {
        return new DefaultInventoryService(context);
    }

    @Bean
    public PurchasesService purchasesService(@Qualifier("purchasesDSLContext") DSLContext context, InventoryService inventory) {
        return new DefaultPurchasesService(context, inventory);
    }
}