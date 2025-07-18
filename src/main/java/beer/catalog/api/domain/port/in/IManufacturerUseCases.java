package beer.catalog.api.domain.port.in;

import beer.catalog.api.domain.model.Manufacturer;
import java.util.List;

public interface IManufacturerUseCases {
    Manufacturer createOrUpdateManufacturer(Long id, String name, String country);
    void deleteManufacturer(Long id);
    List<Manufacturer> getAllManufacturers();
    Manufacturer getManufacturer(Long id);
}
