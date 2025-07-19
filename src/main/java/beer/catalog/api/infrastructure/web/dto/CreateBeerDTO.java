package beer.catalog.api.infrastructure.web.dto;

public record CreateBeerDTO(String name, double ABV, String type, String description,Long idManufacturer) {
}
