package io.github.picodotdev.blogbitix.graphql;

import java.time.LocalDate;
import java.util.List;

public class Magazine extends Publication {
    
    private Long id;
    private String name;
    private Long pages;

    public Magazine(Long id, String name, Long pages) {
        this.id = id;
        this.name = name;
        this.pages = pages;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPages() {
        return pages;
    }

    public void setPages(Long pages) {
        this.pages = pages;
    }
}