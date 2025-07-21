package beer.catalog.api.application.services.beer;

import beer.catalog.api.application.services.security.AuthorizationService;
import beer.catalog.api.domain.exceptions.BeerNotFoundException;
import beer.catalog.api.domain.exceptions.ManufacturerNotFoundException;
import beer.catalog.api.domain.model.Beer;
import beer.catalog.api.domain.model.Manufacturer;
import beer.catalog.api.domain.port.in.IBeerUseCases;
import beer.catalog.api.domain.port.out.IBeerCRUDRepository;
import beer.catalog.api.domain.port.out.IManufacturerCRUDRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeerService implements IBeerUseCases {
    private final IBeerCRUDRepository repository;
    private final IManufacturerCRUDRepository manufacturerRepository;
    private final AuthorizationService authorizationService;

    public BeerService(IBeerCRUDRepository repository, IManufacturerCRUDRepository manufacturerRepository,AuthorizationService authorizationService) {
        this.repository = repository;
        this.manufacturerRepository = manufacturerRepository;
        this.authorizationService = authorizationService;
    }

    @Override
    public Beer createBeer(String name, Double ABV, String type, String description, Long idManufacturer) {
        if(!authorizationService.isAdmin()){
            authorizationService.validateManufacturerAccess(idManufacturer);
        }
        Manufacturer manufacturerEntity = manufacturerRepository.getManufacturer(idManufacturer)
                .orElseThrow(() -> new ManufacturerNotFoundException(idManufacturer.toString()));
        return repository.createBeer(new Beer(null,name, ABV, type, description, manufacturerEntity));
    }


    @Override
    public Beer updateBeer(Long id, String name, Double ABV, String type, String description, Long idManufacturer) {
        if(!authorizationService.isAdmin()){
            authorizationService.validateManufacturerAccess(idManufacturer);
        }
        Manufacturer manufacturerEntity = manufacturerRepository.getManufacturer(idManufacturer)
                .orElseThrow(() -> new ManufacturerNotFoundException(idManufacturer.toString()));
        Beer beerEntity = repository.getBeer(id).orElseThrow(()-> new BeerNotFoundException(id.toString()));
        return repository.updateBeer(new Beer(beerEntity.id(), name, ABV, type, description, manufacturerEntity));
    }

    @Override
    public void deleteBeer(Long id) {
        Beer beer = repository.getBeer(id).orElseThrow(()-> new BeerNotFoundException(id.toString()));
        repository.deleteBeer(beer);
    }

    @Override
    public List<Beer> getAllBeers() {
        return repository.getAllBeers();
    }

    @Override
    public Beer getBeer(Long id) throws BeerNotFoundException {
        return repository.getBeer(id).orElseThrow(()-> new BeerNotFoundException(id.toString()));
    }
}
