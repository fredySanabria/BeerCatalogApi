package beer.catalog.api.application.services.beer;

import beer.catalog.api.domain.exceptions.BeerNotFoundException;
import beer.catalog.api.domain.exceptions.ManufacturerNotFoundException;
import beer.catalog.api.domain.model.Beer;
import beer.catalog.api.domain.model.Manufacturer;
import beer.catalog.api.domain.port.in.IBeerUseCases;
import beer.catalog.api.infrastructure.persistence.adapter.BeerRepositoryAdapter;
import beer.catalog.api.infrastructure.persistence.adapter.ManufacturerRepositoryAdapter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeerService implements IBeerUseCases {
    private final BeerRepositoryAdapter beerRepositoryAdapter;
    private final ManufacturerRepositoryAdapter manufacturerRepositoryAdapter;

    public BeerService(BeerRepositoryAdapter persistenceAdapter, ManufacturerRepositoryAdapter manufacturerRepositoryAdapter) {
        this.beerRepositoryAdapter = persistenceAdapter;
        this.manufacturerRepositoryAdapter = manufacturerRepositoryAdapter;
    }

    @Override
    public Beer createBeer(String name, Double ABV, String type, String description, Long idManufacturer) {
        Manufacturer manufacturerEntity = manufacturerRepositoryAdapter.getManufacturer(idManufacturer)
                .orElseThrow(() -> new ManufacturerNotFoundException(idManufacturer.toString()));
        return beerRepositoryAdapter.createBeer(new Beer(null,name, ABV, type, description, manufacturerEntity));
    }

    @Override
    public Beer updateBeer(Long id, String name, Double ABV, String type, String description, Long idManufacturer) {
        Manufacturer manufacturerEntity = manufacturerRepositoryAdapter.getManufacturer(idManufacturer)
                .orElseThrow(() -> new ManufacturerNotFoundException(idManufacturer.toString()));
        Beer beerEntity = beerRepositoryAdapter.getBeer(id).orElseThrow(()-> new BeerNotFoundException(id.toString()));
        return beerRepositoryAdapter.updateBeer(new Beer(beerEntity.id(), name, ABV, type, description, manufacturerEntity));
    }

    @Override
    public void deleteBeer(Long id) {
        Beer beer = beerRepositoryAdapter.getBeer(id).orElseThrow(()-> new BeerNotFoundException(id.toString()));
        beerRepositoryAdapter.deleteBeer(beer);
    }

    @Override
    public List<Beer> getAllBeers() {
        return beerRepositoryAdapter.getAllBeers();
    }

    @Override
    public Beer getBeer(Long id) throws BeerNotFoundException {
        return beerRepositoryAdapter.getBeer(id).orElseThrow(()-> new BeerNotFoundException(id.toString()));
    }
}
