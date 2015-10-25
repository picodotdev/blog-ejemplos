package io.github.picodotdev.blogbitix.multidatabase.service;

import org.springframework.transaction.annotation.Transactional;

import io.github.picodotdev.blogbitix.multidatabase.jooq.inventory.tables.records.ItemRecord;
import io.github.picodotdev.blogbitix.multidatabase.jooq.purchases.tables.records.PurchaseRecord;
import io.github.picodotdev.blogbitix.multidatabase.spring.AppConfiguration;

@Transactional(transactionManager = AppConfiguration.PURCHASES_TXM)
public interface PurchasesService {

    @Transactional(transactionManager = AppConfiguration.PURCHASES_TXM, readOnly = true)
    Long count();
	PurchaseRecord create(ItemRecord item, long quantity) throws NoStockException;
}