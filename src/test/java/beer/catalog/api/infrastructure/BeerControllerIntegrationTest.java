package beer.catalog.api.infrastructure;

import beer.catalog.api.infrastructure.persistence.entity.BeerEntity;
import beer.catalog.api.infrastructure.persistence.entity.ManufacturerEntity;
import beer.catalog.api.infrastructure.persistence.repository.BeerJPARepository;
import beer.catalog.api.infrastructure.persistence.repository.ManufacturerJPARepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class BeerControllerIntegrationTest {
    @Autowired
    private MockMvc mock;

    @Autowired
    private ManufacturerJPARepository manufacturerRepository;

    @Autowired
    private BeerJPARepository beerRepository;

    @BeforeEach
    void setup() {
        // Create Manufacturer first
        ManufacturerEntity manufacturer = new ManufacturerEntity(null,"Manufacturer test","Test Country");
        manufacturerRepository.save(manufacturer);
    }

    @Test
    void  createBeer_ShouldReturn201AndBeerCreated() throws Exception {
        ManufacturerEntity manufacturer = manufacturerRepository.findAll().getFirst();
        String json = """
    {
        "name": "Aguila Zero",
        "ABV": 0.5,
        "type": "light",
        "description": "Roast Beer",
        "idManufacturer": %d
    }
       \s""".formatted(manufacturer.getId());

        mock.perform(post("/api/beers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Aguila Zero"))
                .andExpect(jsonPath("$.ABV").value(0.5))
                .andExpect(jsonPath("$.type").value("light"))
                .andExpect(jsonPath("$.description").value("Roast Beer"))
                .andExpect(jsonPath("$.idManufacturer").value(manufacturer.getId()));
    }

    @Test
    void  createBeer_ShouldReturn400AndBeerNotCreated() throws Exception {
        ManufacturerEntity manufacturer = manufacturerRepository.findAll().getFirst();
        String json = """
    {
        "name": "Aguila Zero",
        "ABV": xxx,
        "type": "light",
        "description": "Roast Beer",
        "idManufacturer": %d
    }
       \s""".formatted(manufacturer.getId());

        mock.perform(post("/api/beers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void  createBeer_ShouldReturn400AndBeerNotCreatedByManufacturerNotFound() throws Exception {
        String json = """
    {
        "name": "Aguila Zero",
        "ABV": 4.5,
        "type": "light",
        "description": "Roast Beer",
        "idManufacturer": %d
    }
       \s""".formatted(999L);

        mock.perform(post("/api/beers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Beer Manufacturer with id 999 not found"));
    }

    @Test
    void  getBeer_ShouldReturn200AndBeerFound() throws Exception {
        ManufacturerEntity manufacturer = manufacturerRepository.findAll().getFirst();
        BeerEntity beer = new BeerEntity(null,"Turia",4.5,"Roast","Roast Spanish Beer",manufacturer);
        Long beerId = beerRepository.save(beer).getId();

        mock.perform(get("/api/beers/{id}",beerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Turia"))
                .andExpect(jsonPath("$.ABV").value(4.5))
                .andExpect(jsonPath("$.type").value("Roast"))
                .andExpect(jsonPath("$.description").value("Roast Spanish Beer"))
                .andExpect(jsonPath("$.idManufacturer").value(manufacturer.getId()));
    }
    @Test
    void  getBeer_ShouldReturn404AndBeerNotFound() throws Exception {
        Long beerId = 7L;
        mock.perform(get("/api/beers/{id}",beerId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Beer with id 7 not found"));
    }

    @Test
    void  getListBeer_ShouldReturn200AndBeerArrayFound() throws Exception {
        mock.perform(get("/api/beers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void  updateBeer_ShouldReturn200AndBeerUpdated() throws Exception {
        ManufacturerEntity manufacturer = manufacturerRepository.findAll().getFirst();
        BeerEntity beer = new BeerEntity(null,"Turia",4.5,"Roast","Roast Spanish Beer",manufacturer);
        Long beerId = beerRepository.save(beer).getId();
        String json = """
    {
        "id" : %d,
        "name": "Aguila Zero",
        "ABV": 0.5,
        "type": "light",
        "description": "Roast Beer",
        "idManufacturer": %d
    }
       \s""".formatted(beerId, manufacturer.getId());

        mock.perform(put("/api/beers/{id}",beerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Aguila Zero"))
                .andExpect(jsonPath("$.ABV").value(0.5))
                .andExpect(jsonPath("$.type").value("light"))
                .andExpect(jsonPath("$.description").value("Roast Beer"))
                .andExpect(jsonPath("$.idManufacturer").value(manufacturer.getId()));
    }

    @Test
    void  updateBeer_ShouldReturn400AndBeerUpdated() throws Exception {
        ManufacturerEntity manufacturer = manufacturerRepository.findAll().getFirst();
        BeerEntity beer = new BeerEntity(null,"Turia",4.5,"Roast","Roast Spanish Beer",manufacturer);
        Long beerId = beerRepository.save(beer).getId();
        String json = """
    {
        "id" : "70",
        "name": "Aguila Zero",
        "ABV": 0.5,
        "type": "light",
        "description": "Roast Beer",
        "idManufacturer": %d
    }
       \s""".formatted(manufacturer.getId());

        mock.perform(put("/api/beers/{id}",beerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("400 BAD_REQUEST \"Beer id is different from existing in the body\""));
    }

    @Test
    void  updateBeer_ShouldReturn400AndBeerNotUpdatedWhenNotValidData() throws Exception {
        ManufacturerEntity manufacturer = manufacturerRepository.findAll().getFirst();
        BeerEntity beer = new BeerEntity(null,"Turia",4.5,"Roast","Roast Spanish Beer",manufacturer);
        Long beerId = beerRepository.save(beer).getId();

        String json = """
    {
        "id" : "5",
        "name": "Aguila Zero",
        "ABV": xxx,
        "type": "light",
        "description": "Roast Beer",
        "idManufacturer": %d
    }
       \s""".formatted(manufacturer.getId());

        mock.perform(put("/api/beers/{id}",beerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("JSON parse error: Cannot deserialize value"));
    }

    @Test
    void  updateBeer_ShouldReturn400AndBeerNotUpdatedWhenManufacturerNotFound() throws Exception {

        String json = """
    {
        "id" : "70",
        "name": "Aguila Zero",
        "ABV": 0.5,
        "type": "light",
        "description": "Roast Beer",
        "idManufacturer": %d
    }
       \s""".formatted(70L);

        mock.perform(put("/api/beers/{id}",70L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("Beer Manufacturer with id 70 not found"));
    }

    @Test
    void  deleteBeer_ShouldReturn204AndBeerDeleted() throws Exception {
        ManufacturerEntity manufacturer = manufacturerRepository.findAll().getFirst();
        BeerEntity beer = new BeerEntity(null,"Turia",4.5,"Roast","Roast Spanish Beer",manufacturer);
        Long beerId = beerRepository.save(beer).getId();

        mock.perform(delete("/api/beers/{id}",beerId))
                .andExpect(status().isNoContent());
        mock.perform(get("/api/beers/{id}",beerId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Beer with id %d not found".formatted(beerId)));
    }
    @Test
    void  deleteBeer_ShouldReturn404AndBeerNotFound() throws Exception {
        Long beerId = 77L;
        mock.perform(delete("/api/beers/{id}",beerId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Beer with id %d not found".formatted(beerId)));
    }
}
