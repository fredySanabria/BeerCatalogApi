package beer.catalog.api.application.services;

import beer.catalog.api.application.services.beer.BeerService;
import beer.catalog.api.application.services.security.AuthorizationService;
import beer.catalog.api.domain.exceptions.ManufacturerNotFoundException;
import beer.catalog.api.domain.model.Beer;
import beer.catalog.api.domain.model.Manufacturer;
import beer.catalog.api.domain.port.out.IBeerCRUDRepository;
import beer.catalog.api.domain.port.out.IManufacturerCRUDRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class BeerServiceTest {
    @Mock
    IBeerCRUDRepository beerRepository;

    @Mock
    IManufacturerCRUDRepository manufacturerRepository;

    @Mock
    AuthorizationService authorizationService;

    @InjectMocks
    BeerService service;

    @Test
    void shouldToCreateBeerIfManufacturerExist() {
        // Arrange
        Manufacturer manufacturer = new Manufacturer(10L, "Ayinger", "Germany");
        Beer beer = new Beer(1L, "Ayinger Märzen",3.5, "Beer lager","Lager germany beer", manufacturer);
        when(manufacturerRepository.getManufacturer(10L)).thenReturn(Optional.of(manufacturer));
        when(beerRepository.createBeer(any())).thenReturn(beer);
        when(authorizationService.isAdmin()).thenReturn(true);

        // Act
        Beer created = service.createBeer( "Ayinger Märzen",3.5, "Beer lager","Lager germany beer", 10L);

        // Assert
        assertEquals("Ayinger Märzen", created.name());
        assertEquals(10L, created.manufacturer().id());
    }

    @Test
    void shouldFailIfManufacturerNotExist() {
        when(manufacturerRepository.getManufacturer(99L)).thenReturn(Optional.empty());
        when(authorizationService.isAdmin()).thenReturn(true);
        assertThrows(ManufacturerNotFoundException.class,
                () -> service.createBeer(
                        "Ayinger Märzen",
                        3.5,
                        "Beer lager",
                        "Lager germany beer",
                99L));
    }

    @Test
    void deleteBeer_successfulWhenExist() {
        Manufacturer manufacturer = new Manufacturer(10L, "Ayinger", "Germany");
        Beer beer = new Beer(1L, "Ayinger Märzen",3.5, "Beer lager","Lager germany beer", manufacturer);
        // Arrange
        Long id = 1L;
        when(beerRepository.getBeer(id)).thenReturn(Optional.of(beer));

        // Act
        service.deleteBeer(id);

        // Assert
        verify(beerRepository).deleteBeer(beer);
    }
}
