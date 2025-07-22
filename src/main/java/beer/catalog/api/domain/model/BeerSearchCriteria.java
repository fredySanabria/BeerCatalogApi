package beer.catalog.api.domain.model;

public record BeerSearchCriteria(
        String name,
        String type,
        String country,
        String description,
        Double ABVMin,
        Double ABVMax
) {
    public boolean isEmpty() {
        return (name == null || name.isBlank()) &&
                (type == null || type.isBlank()) &&
                (country == null || country.isBlank()) &&
                (description == null || description.isBlank()) &&
                ABVMin == null &&
                ABVMax == null;
    }
}
