package beer.catalog.api.domain.model;

public record Manufacturer (Long id, String name, String country ) {
    public Manufacturer{
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Manufacturer name is mandatory field");
        }
        if (country == null || country.isBlank()) {
            throw new IllegalArgumentException("Manufacturer name is mandatory field");
        }
    }
}
