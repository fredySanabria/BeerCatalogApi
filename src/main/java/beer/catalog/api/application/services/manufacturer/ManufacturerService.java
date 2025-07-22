package beer.catalog.api.application.services.manufacturer;



import beer.catalog.api.application.services.security.AuthorizationService;
import beer.catalog.api.domain.exceptions.ManufacturerNotFoundException;
import beer.catalog.api.domain.exceptions.NoAccessAuthorizationException;
import beer.catalog.api.domain.model.Manufacturer;
import beer.catalog.api.domain.model.ManufacturerSearchCriteria;
import beer.catalog.api.domain.port.in.IManufacturerUseCases;
import beer.catalog.api.domain.port.out.IManufacturerCRUDRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ManufacturerService implements IManufacturerUseCases {
    private final IManufacturerCRUDRepository repository;
    private final AuthorizationService authorizationService;

    public ManufacturerService(IManufacturerCRUDRepository repository,AuthorizationService authorizationService) {
        this.repository = repository;
        this.authorizationService = authorizationService;
    }

    @Override
    public Manufacturer createManufacturer(String name, String country) {
        if (authorizationService.isAdmin()) {
            return repository.createManufacturer(new Manufacturer(null, name, country));
        } else {
            throw new NoAccessAuthorizationException("Just Admin role can create Manufacturers");
        }
    }

    @Override
    public Manufacturer updateManufacturer(Long id, String name, String country) {
        if(!authorizationService.isAdmin()){
            authorizationService.validateManufacturerAccess(id);
        }
        Manufacturer existingManufacturer = repository.getManufacturer(id)
                .orElseThrow(() -> new ManufacturerNotFoundException(id.toString()));
        return repository.updateManufacturer(new Manufacturer(existingManufacturer.id(), name, country));
    }

    @Override
    public void deleteManufacturer(Long id) {
        Manufacturer manufacturer = repository.getManufacturer(id)
                .orElseThrow(() -> new ManufacturerNotFoundException(id.toString()));
        repository.deleteManufacturer(manufacturer);
    }

    @Override
    public List<Manufacturer> getAllManufacturers(Pageable pageable) {
        return repository.getAllManufacturers(pageable);
    }

    @Override
    public List<Manufacturer> getAllManufacturers(ManufacturerSearchCriteria criteria, Pageable pageable) {
        return repository.getAllManufacturers(criteria, pageable);
    }


    @Override
    public Manufacturer getManufacturer(Long id) throws ManufacturerNotFoundException {
        return repository.getManufacturer(id)
                .orElseThrow(() -> new ManufacturerNotFoundException(id.toString()));
    }



}
