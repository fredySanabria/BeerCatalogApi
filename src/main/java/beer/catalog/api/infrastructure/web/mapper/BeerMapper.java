package beer.catalog.api.infrastructure.web.mapper;


import beer.catalog.api.domain.model.Beer;
import beer.catalog.api.infrastructure.web.dto.BeerDTO;

public class BeerMapper {

    public static BeerDTO toDto(Beer beer){
        return new BeerDTO(beer.id(), beer.name(), beer.ABV(), beer.type(), beer.description(), beer.manufacturer().id());
    }
}
