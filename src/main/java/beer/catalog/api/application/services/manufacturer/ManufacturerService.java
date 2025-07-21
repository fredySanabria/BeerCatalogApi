package beer.catalog.api.application.services.manufacturer;



import beer.catalog.api.domain.exceptions.ManufacturerNotFoundException;
import beer.catalog.api.domain.model.Manufacturer;
import beer.catalog.api.domain.port.in.IManufacturerUseCases;
import beer.catalog.api.domain.port.out.IManufacturerCRUDRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerService implements IManufacturerUseCases {
    private final IManufacturerCRUDRepository repository;

    public ManufacturerService(IManufacturerCRUDRepository repository) {
        this.repository = repository;
    }

    @Override
    public Manufacturer createManufacturer(String name, String country) {
        return repository.createManufacturer(new Manufacturer(null,name, country));
    }

    @Override
    public Manufacturer updateManufacturer(Long id, String name, String country) {
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
    public List<Manufacturer> getAllManufacturers() {
        return repository.getAllManufacturers();
    }

    @Override
    public Manufacturer getManufacturer(Long id) throws ManufacturerNotFoundException {
        return repository.getManufacturer(id)
                .orElseThrow(() -> new ManufacturerNotFoundException(id.toString()));
    }

}
