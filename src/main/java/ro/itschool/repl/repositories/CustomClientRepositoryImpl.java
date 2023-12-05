package ro.itschool.repl.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ro.itschool.repl.enums.Utilities;
import ro.itschool.repl.models.entities.Client;
import ro.itschool.repl.models.entities.Property;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CustomClientRepositoryImpl implements CustomClientRepository{

    @PersistenceContext
    private final EntityManager entityManager;

    public List<Property> sortClientPropertiesByUtilitiesStatusPropertyTypeAndYearBuilt(
            Long clientId,
            Utilities utilitiesStatus,
            String propertyType,
            LocalDate yearBuilt) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Property> criteriaQuery = criteriaBuilder.createQuery(Property.class);


        Root<Property> propertyRoot = criteriaQuery.from(Property.class);

        Predicate clientIdPredicate = criteriaBuilder.equal(propertyRoot.get("clients").get("id"), clientId);

        Predicate utilitiesStatusPredicate = (utilitiesStatus != null) ?
                criteriaBuilder.equal(propertyRoot.get("utilitiesStatus"),utilitiesStatus) :
                criteriaBuilder.conjunction();

        Predicate propertyTypePredicate = (propertyType != null && !propertyType.isBlank()) ?
                criteriaBuilder.equal(propertyRoot.get("propertyType"), propertyType) :
                criteriaBuilder.conjunction();

        Predicate yearBuiltPredicate = (yearBuilt != null) ?
                criteriaBuilder.equal(propertyRoot.get("yearBuilt"), yearBuilt) :
                criteriaBuilder.conjunction();

        Predicate finalPredicate = criteriaBuilder.and(
                clientIdPredicate,
                utilitiesStatusPredicate,
                propertyTypePredicate,
                yearBuiltPredicate
        );
        criteriaQuery.where(finalPredicate);

        TypedQuery<Property> typedQuery = entityManager.createQuery(criteriaQuery);

        return typedQuery.getResultList();
    }
}
