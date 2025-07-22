package beer.catalog.api.application.services;

import beer.catalog.api.application.services.manufacturer.ManufacturerService;
import beer.catalog.api.application.services.security.AuthorizationService;
import beer.catalog.api.domain.exceptions.ManufacturerNotFoundException;
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

    @Mock
    AuthorizationService authorizationService;

    @Test
    void shouldToCreateBeer() {
        // when
        Manufacturer manufacturer = new Manufacturer(null, "Manufacturer name", "Germany");
        when(manufacturerRepository.createManufacturer(manufacturer)).thenReturn(manufacturer);
        when(authorizationService.isAdmin()).thenReturn(true);

        // then
        Manufacturer created = service.createManufacturer("Manufacturer name", "Germany");

        // assert
        assertEquals("Manufacturer name", created.name());
    }

    @Test
    void shouldFailIfManufacturerNameNotExist() {
        // Assert
        when(authorizationService.isAdmin()).thenReturn(true);
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

    @Test
    void UpdateManufacturer_successfulWhenExist() {
        when(authorizationService.isAdmin()).thenReturn(true);
        Manufacturer manufacturer = new Manufacturer(1L, "Ayinger", "Germany");
        // When
        Long id = 1L;
        when(manufacturerRepository.getManufacturer(id)).thenReturn(Optional.of(manufacturer));

        // Then
        service.updateManufacturer(id,"New name", "Germany");

        // Assert
        verify(manufacturerRepository).updateManufacturer(new Manufacturer(manufacturer.id(),"New name", "Germany"));
    }

    @Test
    void UpdateManufacturer_FailWhenManufacturerIsNotAllowed() {
        when(authorizationService.isAdmin()).thenReturn(false);
        authorizationService.validateManufacturerAccess(1L);
        // When
        Long id = 1L;
        when(manufacturerRepository.getManufacturer(id)).thenReturn(Optional.empty());

        assertThrows(ManufacturerNotFoundException.class,
                () -> service.updateManufacturer(id,"New name", "Germany"));
    }
}
