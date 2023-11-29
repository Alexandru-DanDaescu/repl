package ro.itschool.repl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.itschool.repl.models.entities.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
}
