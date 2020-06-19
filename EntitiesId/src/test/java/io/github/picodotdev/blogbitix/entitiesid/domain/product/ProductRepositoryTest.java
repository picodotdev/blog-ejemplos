package io.github.picodotdev.blogbitix.entitiesid.domain.product;

import io.github.picodotdev.blogbitix.entitiesid.DefaultPostgresContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(initializers = { DefaultPostgresContainer.Initializer.class })
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testRepositoryGenerateId() {
        // given
        ProductId id = productRepository.generateId();
        Product product = new Product(id, "Raspberry Pi", LocalDate.now(), new BigDecimal("80.0"), 10);

        // and
        productRepository.save(product);

        // then
        assertEquals(product, productRepository.findById(id).get());
    }
}