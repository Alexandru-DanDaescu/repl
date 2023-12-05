package ro.itschool.repl.repositories;

import org.springframework.stereotype.Repository;
import ro.itschool.repl.enums.Utilities;
import ro.itschool.repl.models.entities.Property;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CustomClientRepository {
    List<Property> sortClientPropertiesByUtilitiesStatusPropertyTypeAndYearBuilt(
            Long clientId,
            Utilities utilitiesStatus,
            String propertyType,
            LocalDate yearBuilt);
}
