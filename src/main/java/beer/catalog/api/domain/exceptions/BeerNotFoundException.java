package beer.catalog.api.domain.exceptions;

public class BeerNotFoundException extends RuntimeException {
    public BeerNotFoundException(String id) {
        super("Beer with id "+ id +" not found");
    }
}
