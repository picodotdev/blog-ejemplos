package io.github.picodotdev.blogbitix.multidatabase.service;

import org.springframework.transaction.annotation.Transactional;

import io.github.picodotdev.blogbitix.multidatabase.jooq.inventory.tables.interfaces.IItem;
import io.github.picodotdev.blogbitix.multidatabase.jooq.inventory.tables.records.ItemRecord;
import io.github.picodotdev.blogbitix.multidatabase.spring.AppConfiguration;

@Transactional(transactionManager = AppConfiguration.INVENTORY_TXM)
public interface InventoryService {

    @Transactional(transactionManager = AppConfiguration.INVENTORY_TXM, readOnly = true)
    Long count();    
    ItemRecord create(IItem item);    
    void changeStock(ItemRecord item, Long quantity) throws NoStockException;
}