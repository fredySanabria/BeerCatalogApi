package beer.catalog.api.infrastructure.persistence.specification;

import beer.catalog.api.infrastructure.persistence.entity.ManufacturerEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

/**
 * ManufacturerSpecification:  this Utility class brings all specification method to implement flexible manufacturer search
 */
@Component
public class ManufacturerSpecification {
    /**
     * This method search by name that contains the string parameter
     * @param name -> name or part of name to search
     * @return -> Specification search result
     */
    public static Specification<ManufacturerEntity> nameContains(String name) {
        return (root, query, cb) ->
                name == null ? null : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    /**
     * This method filter the manufacturer search results by country
     * @param country -> country to search
     * @return -> Specification search result
     */
    public static Specification<ManufacturerEntity> countryEquals(String country) {
        return (root, query, cb) ->
                country == null ? null : cb.equal(cb.lower(root.get("country")), country.toLowerCase());
    }
}
