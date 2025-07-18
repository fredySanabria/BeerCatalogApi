package beer.catalog.api.application.services.manufacturer;



import beer.catalog.api.domain.exceptions.ManufacturerNotFoundException;
import beer.catalog.api.domain.model.Manufacturer;
import beer.catalog.api.domain.port.in.IManufacturerUseCases;
import beer.catalog.api.infrastructure.persistence.adapter.ManufacturerRepositoryAdapter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManufacturerService implements IManufacturerUseCases {
    private final ManufacturerRepositoryAdapter persistenceAdapter;

    public ManufacturerService(ManufacturerRepositoryAdapter persistenceAdapter) {
        this.persistenceAdapter = persistenceAdapter;
    }

    @Override
    public Manufacturer createOrUpdateManufacturer(Long id, String name, String country) {
        return persistenceAdapter.createOrUpdateManufacturer(new Manufacturer(id, name, country));
    }

    @Override
    public void deleteManufacturer(Long id) {
        Optional<Manufacturer> manufacturer = persistenceAdapter.getManufacturer(id);
        manufacturer.ifPresent(persistenceAdapter::deleteManufacturer);
    }

    @Override
    public List<Manufacturer> getAllManufacturers() {
        return persistenceAdapter.getAllManufacturers();
    }

    @Override
    public Manufacturer getManufacturer(Long id) throws ManufacturerNotFoundException {
        if(persistenceAdapter.getManufacturer(id).isPresent()){
            return persistenceAdapter.getManufacturer(id).get();
        }else {
            throw new ManufacturerNotFoundException(id.toString());
        }
    }
}
