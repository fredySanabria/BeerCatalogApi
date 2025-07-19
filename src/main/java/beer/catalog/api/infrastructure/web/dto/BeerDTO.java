package beer.catalog.api.infrastructure.web.dto;

public record BeerDTO(Long id, String name, double ABV, String type, String description, Long idManufacturer) {
}
