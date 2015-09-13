package io.github.picodotdev.gradle.web.pages;

import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import io.github.picodotdev.gradle.core.models.tables.records.ItemRecord;
import io.github.picodotdev.gradle.core.services.ItemDAO;

public class Index {

    @Inject
    private ItemDAO itemDAO;
    
    @Property
    private List<ItemRecord> items;
    
    void setupRender() {
        items = itemDAO.findAll();
    }
}