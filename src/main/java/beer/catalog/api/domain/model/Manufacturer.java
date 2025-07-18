package beer.catalog.api.domain.model;

public record Manufacturer (Long id, String name, String country ) {
    public Manufacturer{
        if(id == null || id < 0){
            throw new IllegalArgumentException("Id is mandatory field");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Manufacturer name is mandatory field");
        }
    }
}
