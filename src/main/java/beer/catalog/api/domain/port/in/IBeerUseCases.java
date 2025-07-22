package beer.catalog.api.domain.port.in;

import beer.catalog.api.domain.model.Beer;
import beer.catalog.api.domain.model.BeerSearchCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IBeerUseCases {
    Beer createBeer(String name, Double ABV, String type, String description, Long idManufacturer);
    Beer updateBeer(Long id, String name, Double ABV, String type, String description, Long idManufacturer);
    void deleteBeer(Long id);
    List<Beer> getAllBeers(Pageable pageable);
    List<Beer> getAllBeers(BeerSearchCriteria criteria, Pageable pageable);
    Beer getBeer(Long id);
}
