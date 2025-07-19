package beer.catalog.api.domain.port.in;

import beer.catalog.api.domain.model.Beer;

import java.util.List;

public interface IBeerUseCases {
    Beer createBeer(String name, Double ABV, String type, String description, Long idManufacturer);
    Beer updateBeer(Long id, String name, Double ABV, String type, String description, Long idManufacturer);
    void deleteBeer(Long id);
    List<Beer> getAllBeers();
    Beer getBeer(Long id);
}
