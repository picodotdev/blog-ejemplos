
package io.github.picodotdev.blogbitix.testcontainers;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {

    @Override
    @Modifying
    @Query("delete from Person")
    void deleteAll();
}