package beer.catalog.api.domain.model;

public record ManufacturerSearchCriteria(
        String name,
        String country
) {
    public boolean isEmpty() {
        return (name == null || name.isBlank()) && (country == null || country.isBlank());
    }
}
