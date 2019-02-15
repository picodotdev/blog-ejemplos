package io.github.picodotdev.blogbitix.javainnerclasses;

import java.util.Collection;
import java.math.BigDecimal;
import java.util.stream.Collectors;

public class Order {

    Collection<Product> products;

    // Inner
    public enum Discount {
        NORMAL, DISCOUNT_10, CHEAPEST_FREE
    }

    public Order(Collection<Product> products) {
        this.products = products;
    }

    public BigDecimal calculatePrice(Discount discount) {
        return new PriceCalculatorFactory().getInstance(discount).calculate(products);
    }

    // Inner static
    private static class PriceCalculatorFactory {
        PriceCalculator getInstance(Discount discount) {
            switch (discount) {
                case DISCOUNT_10:
                    return new DiscountCalculator(new BigDecimal("0.90"));                    
                case CHEAPEST_FREE:
                    // Anonymous
                    return new NormalCalculator() {
                        @Override
                        BigDecimal calculate(Collection<Product> products) {
                            Collection<Product> paid = products.stream().sorted().skip(1).collect(Collectors.toList());
                            return super.calculate(paid);
                        }
                    };
                case NORMAL:
                default:
                  return new NormalCalculator();
            }
        }
    }

    // Inner static
    private static abstract class PriceCalculator {
        abstract BigDecimal calculate(Collection<Product> products);
    }

    // Inner static
    private static class NormalCalculator extends PriceCalculator {

        @Override
        BigDecimal calculate(Collection<Product> products) {
            return products.stream().map(i -> i.getPrice()).reduce(new BigDecimal("0.00"), (a, b) -> { return a.add(b); });
        }
    }

    // Inner static
    private static class DiscountCalculator extends PriceCalculator {

        private BigDecimal discount;

        public DiscountCalculator(BigDecimal discount) {
            this.discount = discount;
        }

        @Override
        BigDecimal calculate(Collection<Product> products) {
            PriceCalculator calculator = new NormalCalculator();
            return calculator.calculate(products).multiply(discount);
        }
    }
}
