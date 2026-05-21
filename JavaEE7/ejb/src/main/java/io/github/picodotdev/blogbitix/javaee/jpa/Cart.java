package io.github.picodotdev.blogbitix.javaee.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Cart implements Serializable {

    public static Cart EMPTY = new Cart();

    private List<Map<String, String>> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public List<Map<String, String>> getItems() {
        return items;
    }

    public void setItems(List<Map<String, String>> items) {
        this.items = items;
    }
}
