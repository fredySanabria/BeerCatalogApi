package beer.catalog.api.domain.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BeerTest {
    Manufacturer beerManufacturer;

    @BeforeEach
    void setUp() {
        beerManufacturer = new Manufacturer(1L,"Paulander", "Germany");
    }

    @Test
    void CreateValidBeer_Successfully(){
        Beer myTestBeer = new Beer(1,"Perfect Larger",4.5,"larger","Beer description", beerManufacturer);
        assertEquals("Perfect Larger", myTestBeer.name());
        assertEquals("Paulander",myTestBeer.manufacturer().name());
    }

    @Test
    void CreateBeerWithoutManufacturer_ThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> new Beer(1,"Perfect Larger",4.5,"larger","Beer description", null));
    }

    @Test
    void CreateBeerWithoutValidABV_ThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> new Beer(1,"Perfect Larger",-114.5,"larger","Beer description", beerManufacturer));
    }
}