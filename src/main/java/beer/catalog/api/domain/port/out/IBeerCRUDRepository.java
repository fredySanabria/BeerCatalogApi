package beer.catalog.api.domain.port.out;

import beer.catalog.api.domain.model.Beer;
import beer.catalog.api.domain.model.BeerSearchCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IBeerCRUDRepository {
    Beer createBeer(Beer beer);
    Beer updateBeer(Beer beer);
    void deleteBeer(Beer beer);
    List<Beer> getAllBeers(Pageable pageable);
    Optional<Beer> getBeer(Long id);
    List<Beer> getBeersByFilter(BeerSearchCriteria criteria, Pageable pageable);
}
