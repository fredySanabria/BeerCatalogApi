package beer.catalog.api.domain.port.out;

import beer.catalog.api.domain.model.Beer;

import java.util.List;
import java.util.Optional;

public interface IBeerCRUDRepository {
    Beer createBeer(Beer beer);
    Beer updateBeer(Beer beer);
    void deleteBeer(Beer beer);
    List<Beer> getAllBeers();
    Optional<Beer> getBeer(Long id);
}
