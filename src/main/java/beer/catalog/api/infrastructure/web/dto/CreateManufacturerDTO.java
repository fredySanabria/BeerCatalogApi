package beer.catalog.api.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateManufacturerDTO(
        @NotBlank(message = "Name can't be empty")
        @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúñÑ\\s]{2,50}$", message = "The name can only contain letters and spaces")
        String name,
        @NotBlank(message = "Country can't be empty")
        @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúñÑ\\s]{2,50}$", message = "The country can only contain letters and spaces")
        String country) {}
