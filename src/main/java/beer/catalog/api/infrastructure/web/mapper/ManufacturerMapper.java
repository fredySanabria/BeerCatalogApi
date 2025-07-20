package beer.catalog.api.infrastructure.web.mapper;


import beer.catalog.api.domain.model.Manufacturer;
import beer.catalog.api.infrastructure.web.dto.ManufacturerDTO;

public class ManufacturerMapper {

    public static ManufacturerDTO toDto(Manufacturer beerManufacturer){
        return new ManufacturerDTO(beerManufacturer.id(), beerManufacturer.name(), beerManufacturer.country());
    }
}
