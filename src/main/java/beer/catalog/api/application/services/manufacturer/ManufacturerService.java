package beer.catalog.api.application.services.manufacturer;



import beer.catalog.api.domain.exceptions.ManufacturerNotFoundException;
import beer.catalog.api.domain.model.Manufacturer;
import beer.catalog.api.domain.port.in.IManufacturerUseCases;
import beer.catalog.api.infrastructure.persistence.adapter.ManufacturerRepositoryAdapter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerService implements IManufacturerUseCases {
    private final ManufacturerRepositoryAdapter persistenceAdapter;

    public ManufacturerService(ManufacturerRepositoryAdapter persistenceAdapter) {
        this.persistenceAdapter = persistenceAdapter;
    }

    @Override
    public Manufacturer createManufacturer(String name, String country) {
        return persistenceAdapter.createManufacturer(new Manufacturer(null,name, country));
    }

    @Override
    public Manufacturer updateManufacturer(Long id, String name, String country) {
        Manufacturer existingManufacturer = persistenceAdapter.getManufacturer(id)
                .orElseThrow(() -> new ManufacturerNotFoundException(id.toString()));
        return persistenceAdapter.updateManufacturer(new Manufacturer(existingManufacturer.id(), name, country));
    }

    @Override
    public void deleteManufacturer(Long id) {
        Manufacturer manufacturer = persistenceAdapter.getManufacturer(id)
                .orElseThrow(() -> new ManufacturerNotFoundException(id.toString()));
        persistenceAdapter.deleteManufacturer(manufacturer);
    }

    @Override
    public List<Manufacturer> getAllManufacturers() {
        return persistenceAdapter.getAllManufacturers();
    }

    @Override
    public Manufacturer getManufacturer(Long id) throws ManufacturerNotFoundException {
        return persistenceAdapter.getManufacturer(id)
                .orElseThrow(() -> new ManufacturerNotFoundException(id.toString()));
    }

}
