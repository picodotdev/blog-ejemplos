package io.github.picodotdev.blogbitix.multidatabase.service;

import io.github.picodotdev.blogbitix.multidatabase.jooq.inventory.tables.interfaces.IItem;
import io.github.picodotdev.blogbitix.multidatabase.jooq.inventory.tables.records.ItemRecord;

public interface InventoryService {

    Long count();
    ItemRecord create(IItem item);
    void changeStock(ItemRecord item, Long quantity) throws NoStockException;
}