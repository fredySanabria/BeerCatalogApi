package beer.catalog.api.infrastructure.persistence.mapper;


import beer.catalog.api.domain.model.Manufacturer;
import beer.catalog.api.infrastructure.persistence.entity.ManufacturerEntity;

public class ManufacturerMapper {
    public static ManufacturerEntity toEntity(Manufacturer beerManufacturer){
        return new ManufacturerEntity(
                beerManufacturer.id(),
                beerManufacturer.name(),
                beerManufacturer.country()
        );
    }

    public static Manufacturer toDomain(ManufacturerEntity beerManufacturerEntity){
        return new Manufacturer(
                beerManufacturerEntity.getId(),
                beerManufacturerEntity.getName(),
                beerManufacturerEntity.getCountry());
    }
}
