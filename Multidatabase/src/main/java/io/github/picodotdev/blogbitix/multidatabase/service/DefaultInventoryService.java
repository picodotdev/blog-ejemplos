package io.github.picodotdev.blogbitix.multidatabase.service;

import org.jooq.DSLContext;

import io.github.picodotdev.blogbitix.multidatabase.jooq.inventory.Tables;
import io.github.picodotdev.blogbitix.multidatabase.jooq.inventory.tables.interfaces.IItem;
import io.github.picodotdev.blogbitix.multidatabase.jooq.inventory.tables.records.ItemRecord;

public class DefaultInventoryService implements InventoryService {

    private DSLContext context;

    public DefaultInventoryService(DSLContext context) {
		this.context = context;
	}

    @Override
    public Long count() {
        return context.selectCount().from(Tables.ITEM).fetchOne(0, Long.class);
    }

    @Override
    public ItemRecord create(IItem item) {
        ItemRecord record = context.newRecord(Tables.ITEM);
        record.from(item);        
        record.insert();        
        return record;
    }
    
    @Override
    public void changeStock(ItemRecord item, Long quantity) throws NoStockException {
        Long stock = item.getStock() - quantity;
        if (stock < 0) {
            throw new NoStockException();
        }
        item.setStock(stock);
        item.update();
    }
}