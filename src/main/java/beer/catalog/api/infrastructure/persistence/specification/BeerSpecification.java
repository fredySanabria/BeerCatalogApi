package beer.catalog.api.infrastructure.persistence.specification;

import beer.catalog.api.infrastructure.persistence.entity.BeerEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

/**
 * BeerSpecification:  this Utility class brings all specification method to implement flexible beer search
 */
@Component
public class BeerSpecification {
    /**
     * This method search by name that contains the string parameter
     * @param name -> name or part of name to search
     * @return -> Specification search result
     */
    public static Specification<BeerEntity> nameContains(String name) {
        return (root, query, cb) ->
                name == null ? null : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    /**
     * This method filter the beer search results by type
     * @param type -> type of beer to filter
     * @return -> Specification search result
     */
    public static Specification<BeerEntity> typeEquals(String type) {
        return (root, query, cb) ->
                type == null ? null : cb.equal(cb.lower(root.get("type")), type.toLowerCase());
    }

    /**
     * This method filter the beer search results by country
     * @param country -> country to search
     * @return -> Specification search result
     */
    public static Specification<BeerEntity> countryEquals(String country) {
        return (root, query, cb) ->
                country == null ? null : cb.equal(cb.lower(root.get("manufacturer").get("country")), country.toLowerCase());
    }

    /**
     * This method search by description that contains the text parameter
     * @param text -> text to find inside description
     * @return -> Specification search result
     */
    public static Specification<BeerEntity> descriptionContains(String text) {
        return (root, query, cb) ->
                text == null ? null : cb.like(cb.lower(root.get("description")), "%" + text.toLowerCase() + "%");
    }

    /**
     * This method filter the beer search results by abv Range
     * @param min abv minimum parameter
     * @param max abv maximum parameter
     * @return -> Specification search result
     */
    public static Specification<BeerEntity> abvBetween(Double min, Double max) {
        return (root, query, cb) -> {
            if (min == null && max == null) return null;
            if (min != null && max != null) return cb.between(root.get("ABV"), min, max);
            if (min != null) return cb.greaterThanOrEqualTo(root.get("ABV"), min);
            return cb.lessThanOrEqualTo(root.get("ABV"), max);
        };
    }
}
