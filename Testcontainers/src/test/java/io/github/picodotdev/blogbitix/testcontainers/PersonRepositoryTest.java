package io.github.picodotdev.blogbitix.testcontainers;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.ContextConfiguration;

import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
@ContextConfiguration(initializers = { DefaultPostgresContainer.Initializer.class })
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @AfterEach
    void afterEach() {
        personRepository.deleteAll();
    }

    @Test
    void repositoryPersonCount() {
        // given
        List<Person> persons = List.of(new Person("James Gosling"), new Person("Linus Torvalds"), new Person("Richard Stallman"), new Person("Bill Gates"), new Person("Steve Jobs"), new Person("Dennis Ritchie"));
        personRepository.saveAll(persons);

        // then
        assertEquals(persons.size(), personRepository.count());
    }

    @Test
    @Sql("/sql/persons.sql")
    void sqlPersonCount() {
        // then
        assertEquals(6, personRepository.count());
    }
}