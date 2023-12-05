package ro.itschool.repl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.itschool.repl.enums.Utilities;
import ro.itschool.repl.models.entities.Client;
import ro.itschool.repl.models.entities.Property;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>, CustomClientRepository {
}
