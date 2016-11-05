package io.github.picodotdev.blogbitix.holamundojavers;

import javax.persistence.Id;

public class Category {

    @Id
    private String name;

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
