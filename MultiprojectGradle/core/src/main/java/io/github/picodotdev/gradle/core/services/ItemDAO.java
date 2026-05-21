package io.github.picodotdev.gradle.core.services;

import java.util.List;

import io.github.picodotdev.gradle.core.models.tables.records.ItemRecord;

public interface ItemDAO {

    List<ItemRecord> findAll();
    void add(ItemRecord item);    
    void delete(ItemRecord item);    
}