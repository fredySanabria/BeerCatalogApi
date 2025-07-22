package beer.catalog.api.domain.port.in;

import beer.catalog.api.domain.model.Manufacturer;
import beer.catalog.api.domain.model.ManufacturerSearchCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IManufacturerUseCases {
    Manufacturer createManufacturer(String name, String country);
    Manufacturer updateManufacturer(Long id, String name, String country);
    void deleteManufacturer(Long id);
    List<Manufacturer> getAllManufacturers(Pageable pageable);
    List<Manufacturer> getAllManufacturers(ManufacturerSearchCriteria criteria, Pageable pageable);
    Manufacturer getManufacturer(Long id);
}
