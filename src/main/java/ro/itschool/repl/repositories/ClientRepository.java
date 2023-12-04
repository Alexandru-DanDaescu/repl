package ro.itschool.repl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.itschool.repl.models.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
