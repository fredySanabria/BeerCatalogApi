package beer.catalog.api.infrastructure.persistence.mapper;


import beer.catalog.api.domain.model.Beer;
import beer.catalog.api.infrastructure.persistence.entity.BeerEntity;

public class BeerMapper {
    public static BeerEntity toEntity(Beer beer){
        return new BeerEntity(
                beer.id(),
                beer.name(),
                beer.ABV(),
                beer.type(),
                beer.description(),
                ManufacturerMapper.toEntity(beer.manufacturer())
        );
    }

    public static Beer toDomain(BeerEntity beerEntity){
        return new Beer(
                beerEntity.getId(),
                beerEntity.getName(),
                beerEntity.getABV(),
                beerEntity.getType(),
                beerEntity.getDescription(),
                ManufacturerMapper.toDomain(beerEntity.getManufacturer())
        );
    }
}
