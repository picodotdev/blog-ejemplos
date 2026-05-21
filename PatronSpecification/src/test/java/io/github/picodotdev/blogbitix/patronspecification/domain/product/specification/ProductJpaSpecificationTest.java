package io.github.picodotdev.blogbitix.patronspecification.domain.product.specification;

import io.github.picodotdev.blogbitix.patronspecification.DefaultPostgresContainer;
import io.github.picodotdev.blogbitix.patronspecification.domain.product.Product;
import io.github.picodotdev.blogbitix.patronspecification.domain.product.ProductRepository;
import liquibase.pro.packaged.T;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(initializers = { DefaultPostgresContainer.Initializer.class })
public class ProductJpaSpecificationTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @Sql("/sql/products.sql")
    void testIsCheapSpecification() {
        // given
        Specification<Product> specification = new IsCheapSpecification();

        // then
        assertEquals(1, productRepository.findAll(specification).size());
    }

    @Test
    @Sql("/sql/products.sql")
    void testIsLongTermSpecification() {
        // given
        Specification<Product> specification = new IsLongTermSpecification();

        // then
        assertEquals(1, productRepository.findAll(specification).size());
    }

    @Test
    @Sql("/sql/products.sql")
    void testIsOverstockSpecification() {
        // given
        Specification<Product> specification = new IsOverstockSpecification();

        // then
        assertEquals(1, productRepository.findAll(specification).size());
    }

    @Test
    @Sql("/sql/products.sql")
    void testAndSpecification() {
        // given
        Specification<Product> specification = new EqualsSpecification("name", "Raspberry Pi").and(new EqualsSpecification("price", new BigDecimal("80.0"))).and(new EqualsSpecification("units", 10));

        // then
        assertEquals(1, productRepository.findAll(specification).size());
    }

    @Test
    @Sql("/sql/products.sql")
    void testOrSpecification() {
        // given
        Specification<Product> specificationA = new EqualsSpecification("name", "Raspberry Pi").or(new EqualsSpecification("price", new BigDecimal("0.0"))).or(new EqualsSpecification("units", 0));
        Specification<Product> specificationB = new EqualsSpecification("name", "").or(new EqualsSpecification("price", new BigDecimal("80.0"))).or(new EqualsSpecification("units", 0));
        Specification<Product> specificationC = new EqualsSpecification("name", "").or(new EqualsSpecification("price", new BigDecimal("0.0"))).or(new EqualsSpecification("units", 50));
        Specification<Product> specificationZ = new EqualsSpecification("name", "").or(new EqualsSpecification("price", new BigDecimal("0.0"))).or(new EqualsSpecification("units", 0));

        // then
        assertEquals(1, productRepository.findAll(specificationA).size());
        assertEquals(1, productRepository.findAll(specificationB).size());
        assertEquals(1, productRepository.findAll(specificationC).size());
        assertEquals(0, productRepository.findAll(specificationZ).size());
    }

    @Test
    @Sql("/sql/products.sql")
    void testNotSpecification() {
        // given
        Specification<Product> specification = Specification.not(new EqualsSpecification("name", "Raspberry Pi").and(new EqualsSpecification("price", new BigDecimal("80.0"))).and(new EqualsSpecification("units", 50)));

        // then
        assertEquals(7, productRepository.findAll(specification).size());
    }

    private <T> Specification<T> equalsSpecification(String property, Object value) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("name"), "Raspberry Pi");
        };
    }
}