package io.github.picodotdev.blogbitix.javainnerclasses;

import java.util.Collection;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Main {

    public static void main(String[] args) {
        Collection<Product> products = new ArrayList<>();
        products.add(new Product(new BigDecimal("5.0")));
        products.add(new Product(new BigDecimal("10.0")));
        products.add(new Product(new BigDecimal("15.0")));
        Order order = new Order(products);

        DecimalFormat df = new DecimalFormat("#,###.00");

        System.out.printf("Price (normal): %s%n", df.format(order.calculatePrice(Order.Discount.NORMAL)));
        System.out.printf("Price (discount 10%%): %s%n", df.format(order.calculatePrice(Order.Discount.DISCOUNT_10)));
        System.out.printf("Price (chapest free): %s%n", df.format(order.calculatePrice(Order.Discount.CHEAPEST_FREE)));
    }
}
