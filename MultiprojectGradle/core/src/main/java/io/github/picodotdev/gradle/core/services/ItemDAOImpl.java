package io.github.picodotdev.gradle.core.services;

import java.util.List;

import org.jooq.DSLContext;

import io.github.picodotdev.gradle.core.models.Tables;
import io.github.picodotdev.gradle.core.models.tables.records.ItemRecord;

public class ItemDAOImpl implements ItemDAO {

    private DSLContext context;
    
    public ItemDAOImpl(DSLContext context) {
        this.context = context;
    }
    
    public void add(ItemRecord item) {
    	item.store();
    }
    
    public void delete(ItemRecord item) {
    	item.delete();
    }
    
    public List<ItemRecord> findAll() {
        return context.selectFrom(Tables.ITEM).fetch();
    }
}
