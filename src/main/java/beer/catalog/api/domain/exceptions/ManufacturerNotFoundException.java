package beer.catalog.api.domain.exceptions;

public class ManufacturerNotFoundException extends RuntimeException {
    public ManufacturerNotFoundException(String id) {
        super("Beer Manufacturer with id "+ id +" not found");
    }
}
