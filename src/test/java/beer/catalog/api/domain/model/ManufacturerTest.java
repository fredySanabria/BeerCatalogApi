package beer.catalog.api.domain.model;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ManufacturerTest {
    @Test
    void CreateValidManufacturer_Successfully(){
        Manufacturer beerManufacturer = new Manufacturer(2L,"Moritz", "España");
        assertEquals("Moritz",beerManufacturer.name());
        assertEquals(2,beerManufacturer.id());
    }

    @Test
    void CreateManufacturerWithoutName_ThrowsException(){
        assertThrows(IllegalArgumentException.class,() -> new Manufacturer(2L,null, "España"));
    }
    @Test
    void CreateManufacturerWithoutId_ThrowsException(){
        assertThrows(IllegalArgumentException.class,() -> new Manufacturer(null,"Moritz", "España"));
    }
}
