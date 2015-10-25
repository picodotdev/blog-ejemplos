package io.github.picodotdev.blogbitix.multidatabase.service;

import io.github.picodotdev.blogbitix.multidatabase.jooq.inventory.tables.records.ItemRecord;
import io.github.picodotdev.blogbitix.multidatabase.jooq.purchases.tables.records.PurchaseRecord;

public interface PurchasesService {

    Long count();
	PurchaseRecord create(ItemRecord item, long quantity) throws NoStockException;
}