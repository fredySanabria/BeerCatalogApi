package beer.catalog.api.application.services;

import beer.catalog.api.application.services.manufacturer.ManufacturerService;
import beer.catalog.api.domain.model.Manufacturer;
import beer.catalog.api.domain.port.out.IManufacturerCRUDRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ManufacturerServiceTest {

    @Mock
    IManufacturerCRUDRepository manufacturerRepository;

    @InjectMocks
    ManufacturerService service;

    @Test
    void shouldToCreateBeerIfManufacturerExist() {
        // when
        Manufacturer manufacturer = new Manufacturer(null, "Manufacturer name", "Germany");
        when(manufacturerRepository.createManufacturer(manufacturer)).thenReturn(manufacturer);

        // then
        Manufacturer created = service.createManufacturer("Manufacturer name", "Germany");

        // assert
        assertEquals("Manufacturer name", created.name());
    }

    @Test
    void shouldFailIfManufacturerNameNotExist() {
        // Assert
        assertThrows(IllegalArgumentException.class,
                () -> service.createManufacturer(
                        null,
                        "country"));
    }
    @Test
    void deleteBeer_successfulWhenExist() {
        Manufacturer manufacturer = new Manufacturer(10L, "Ayinger", "Germany");
        // When
        Long id = 1L;
        when(manufacturerRepository.getManufacturer(id)).thenReturn(Optional.of(manufacturer));

        // Then
        service.deleteManufacturer(id);

        // Assert
        verify(manufacturerRepository).deleteManufacturer(manufacturer);
    }
}
