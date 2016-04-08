package io.github.picodotdev.blogbitix.javaee.jar;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.naming.Context;
import javax.naming.InitialContext;

import io.github.picodotdev.blogbitix.javaee.ejb.SupermarketRemote;
import io.github.picodotdev.blogbitix.javaee.jpa.Product;
import io.github.picodotdev.blogbitix.javaee.jpa.Purchase;

public class SupermarketClient {

    public static void main(String[] args) throws Exception {
        Context context = new InitialContext();
        SupermarketRemote supermarket = (SupermarketRemote) context.lookup("ejb:ear/ejb/Supermarket!io.github.picodotdev.blogbitix.javaee.ejb.SupermarketRemote");

        List<Product> products = supermarket.findProducts();
        List<Purchase> purchases = supermarket.findPurchases();

        printProductsSummary(products);
        printPurchasesSummary(purchases);
    }

    private static void printProductsSummary(List<Product> products) {
        BigDecimal maxPriceProduct = products.stream().map(a -> {
            return a.getPrice();
        }).sorted((a, b) -> {
            return a.compareTo(b);
        }).findFirst().orElse(new BigDecimal("0"));
        BigDecimal minPriceProduct = products.stream().map(a -> {
            return a.getPrice();
        }).sorted((a, b) -> {
            return b.compareTo(a);
        }).findFirst().orElse(new BigDecimal("0"));
        BigDecimal sumPriceProduct = products.stream().map(p -> p.getPrice()).collect(Collectors.reducing(new BigDecimal("0"), (result, element) -> {
            return result.add(element);
        }));
        BigDecimal avgPriceProduct = (products.size() == 0) ? new BigDecimal("0") : sumPriceProduct.divide(new BigDecimal(products.size()));
        Double avgStockProduct = products.stream().collect(Collectors.averagingDouble(p -> {
            return p.getPrice().doubleValue();
        }));
        System.out.printf("Products summary(count: %d, maxPrice: %.2f, minPrice: %.2f, avgPrice: %.2f, avgStock: %.2f%n", products.size(), maxPriceProduct, minPriceProduct,
                avgPriceProduct, avgStockProduct);
    }

    private static void printPurchasesSummary(List<Purchase> purchases) {
        Integer numProducts = purchases.size();
        BigDecimal maxPriceProduct = purchases.stream().map(a -> {
            return a.getPrice();
        }).sorted((a, b) -> {
            return a.compareTo(b);
        }).findFirst().orElse(new BigDecimal("0"));
        BigDecimal minPriceProduct = purchases.stream().map(a -> {
            return a.getPrice();
        }).sorted((a, b) -> {
            return b.compareTo(a);
        }).findFirst().orElse(new BigDecimal("0"));
        BigDecimal sumPriceProduct = purchases.stream().map(p -> p.getPrice()).collect(Collectors.reducing(new BigDecimal("0"), (result, element) -> {
            return result.add(element);
        }));
        BigDecimal avgPriceProduct = (purchases.size() == 0) ? new BigDecimal("0") : sumPriceProduct.divide(new BigDecimal(numProducts));
        Double avgStockProduct = purchases.stream().collect(Collectors.averagingDouble(p -> {
            return p.getPrice().doubleValue();
        }));
        System.out.printf("Purchases summary(count: %d, maxPrice: %.2f, minPrice: %.2f, avgPrice: %.2f, avgStock: %.2f%n", purchases.size(),
                maxPriceProduct, minPriceProduct, avgPriceProduct, avgStockProduct);
    }
}