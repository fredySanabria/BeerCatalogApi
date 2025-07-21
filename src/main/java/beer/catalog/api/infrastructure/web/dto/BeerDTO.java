package beer.catalog.api.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record BeerDTO(
        Long id,
        @NotBlank(message = "Name can't be empty")
        @Pattern(regexp = "^[\\p{L}\\p{M}\\s]{2,50}$", message = "The name can only contain letters and spaces")
        String name,
        double ABV,
        String type,
        String description,
        Long idManufacturer) {
}
