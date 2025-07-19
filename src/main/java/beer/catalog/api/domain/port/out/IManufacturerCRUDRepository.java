package beer.catalog.api.domain.port.out;

import beer.catalog.api.domain.model.Manufacturer;

import java.util.List;
import java.util.Optional;

public interface IManufacturerCRUDRepository {
    Manufacturer createManufacturer(Manufacturer beerManufacturer);
    Manufacturer updateManufacturer(Manufacturer beerManufacturer);
    void deleteManufacturer(Manufacturer beerManufacturer);
    List<Manufacturer> getAllManufacturers();
    Optional<Manufacturer> getManufacturer(Long id);
}
