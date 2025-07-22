package beer.catalog.api.domain.port.out;

import beer.catalog.api.domain.model.Manufacturer;
import beer.catalog.api.domain.model.ManufacturerSearchCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IManufacturerCRUDRepository {
    Manufacturer createManufacturer(Manufacturer beerManufacturer);
    Manufacturer updateManufacturer(Manufacturer beerManufacturer);
    void deleteManufacturer(Manufacturer beerManufacturer);
    List<Manufacturer> getAllManufacturers(Pageable pageable);
    List<Manufacturer> getAllManufacturers(ManufacturerSearchCriteria criteria, Pageable pageable);
    Optional<Manufacturer> getManufacturer(Long id);
}
