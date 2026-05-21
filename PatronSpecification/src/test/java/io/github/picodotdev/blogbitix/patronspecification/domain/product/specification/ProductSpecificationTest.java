package io.github.picodotdev.blogbitix.patronspecification.domain.product.specification;

import java.math.BigDecimal;
import java.time.LocalDate;

import io.github.picodotdev.blogbitix.patronspecification.domain.product.Product;
import io.github.picodotdev.blogbitix.patronspecification.specification.AndSpecification;
import io.github.picodotdev.blogbitix.patronspecification.specification.NotSpecification;
import io.github.picodotdev.blogbitix.patronspecification.specification.OrSpecification;
import io.github.picodotdev.blogbitix.patronspecification.specification.Specification;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(initializers = { ProductSpecificationTest.Initializer.class })
public class ProductSpecificationTest {

    @Test
    void testEqualsSpecification() {
        // given
        Product product = new Product("Raspberry Pi", LocalDate.now(), new BigDecimal("80.0"), 10);

        // and
        Specification<Product> specification = new EqualsSpecification("name", "Raspberry Pi");

        // then
        assertTrue(product.satisfies(specification));
    }

    @Test
    void testIsCheapSpecification() {
        // given
        Product product = new Product("Pin", LocalDate.now(),new BigDecimal("1.0"), 50);

        // and
        Specification<Product> specification = new IsCheapSpecification();

        // then
        assertTrue(product.satisfies(specification));
    }

    @Test
    void testIsLongTermSpecification() {
        // given
        Product product = new Product("Raspberry Pi", LocalDate.now().minus(Product.LONG_TERM_PERIOD).minusDays(1),new BigDecimal("80.0"), 10);

        // and
        Specification<Product> specification = new IsLongTermSpecification();

        // then
        assertTrue(product.satisfies(specification));
    }

    @Test
    void testIsOverstockSpecification() {
        // given
        Product product = new Product("Pin", LocalDate.now(),new BigDecimal("5.0"), 50);

        // and
        Specification<Product> specification = new IsOverstockSpecification();

        // then
        assertTrue(product.satisfies(specification));
    }

    @Test
    void testIsCheapAndIsLongTermSpecification() {
        // given
        Product product = new Product("Pin", LocalDate.now().minus(Product.LONG_TERM_PERIOD).minusDays(1),new BigDecimal("1.0"), 50);

        // and
        Specification<Product> cheapSpecification = new IsCheapSpecification();
        Specification<Product> longTermSpecification = new IsLongTermSpecification();
        Specification<Product> specification = new AndSpecification<>(cheapSpecification, longTermSpecification);

        // then
        assertTrue(product.satisfies(specification));
    }

    @Test
    void testAndSpecification() {
        // given
        Product product = new Product("Raspberry Pi",LocalDate.now(), new BigDecimal("80.0"), 10);

        // and
        Specification<Product> specification = new AndSpecification<>(new EqualsSpecification("name", "Raspberry Pi"), new EqualsSpecification("price", new BigDecimal("80.0")), new EqualsSpecification("units", 10));

        // then
        assertTrue(product.satisfies(specification));
    }

    @Test
    void testOrSpecification() {
        // given
        Product product = new Product("Raspberry Pi", LocalDate.now(), new BigDecimal("80.0"), 50);

        // and
        Specification<Product> specificationA = new OrSpecification<>(new EqualsSpecification("name", "Raspberry Pi"), new EqualsSpecification("price", new BigDecimal("1.0")), new EqualsSpecification("units", 0));
        Specification<Product> specificationB = new OrSpecification<>(new EqualsSpecification("name", ""), new EqualsSpecification("price", new BigDecimal("80.0")), new EqualsSpecification("units", 0));
        Specification<Product> specificationC = new OrSpecification<>(new EqualsSpecification("name", ""), new EqualsSpecification("price", new BigDecimal("0.0")), new EqualsSpecification("units", 50));
        Specification<Product> specificationZ = new OrSpecification<>(new EqualsSpecification("name", ""), new EqualsSpecification("price", new BigDecimal("0.0")), new EqualsSpecification("units", 0));

        // then
        assertTrue(product.satisfies(specificationA));
        assertTrue(product.satisfies(specificationB));
        assertTrue(product.satisfies(specificationC));
        assertFalse(product.satisfies(specificationZ));
    }

    @Test
    void testNotSpecification() {
        // given
        Product product = new Product("Raspberry Pi", LocalDate.now(), new BigDecimal("80.0"), 10);

        // and
        Specification<Product> specification = new NotSpecification<>(new AndSpecification<Product>(new EqualsSpecification("name", "Raspberry Pi"), new EqualsSpecification("price", new BigDecimal("80.0")), new EqualsSpecification("units", 10)));

        // then
        assertFalse(product.satisfies(specification));
    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of("spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration")
                    .applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}