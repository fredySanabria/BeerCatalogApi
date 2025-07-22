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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class AuthorizationIntegrationTest {
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
    @WithMockUser(username = "2-Peter", roles = {"MANUFACTURER"})
    void  createBeer_ShouldReturn201AndBeerCreated() throws Exception {
        ManufacturerEntity manufacturer = manufacturerRepository.findAll().get(1);
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
    @WithMockUser(username = "2-Peter", roles = {"MANUFACTURER"})
    void  createBeer_ShouldReturn403AndBeerNotCreated() throws Exception {
        ManufacturerEntity manufacturer = manufacturerRepository.findAll().getFirst();
        String json = """
    {
        "name": "Aguila Zero",
        "ABV": 4.5,
        "type": "light",
        "description": "Roast Beer",
        "idManufacturer": %d
    }
       \s""".formatted(manufacturer.getId());

        mock.perform(post("/api/beers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("Your user doesn't have permissions to perform this operation"));
    }

    @Test
    @WithMockUser(username = "999-Peter", roles = {"MANUFACTURER"})
    void  createBeer_ShouldReturn403AndBeerNotCreatedAnd() throws Exception {
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
    @WithMockUser(username = "JhonDoe", roles = {"ANONYMOUS"})
    void  getBeer_ShouldReturn200AndBeerFoundWithAnonymousUser() throws Exception {
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
    @WithMockUser(username = "2-Peter", roles = {"MANUFACTURER"})
    void  updateBeer_ShouldReturn403ByNotAuthorizedUserAndBeerNotUpdated() throws Exception {
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
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("Your user doesn't have permissions to perform this operation"));
    }

    @Test
    @WithMockUser(username = "2-Peter", roles = {"MANUFACTURER"})
    void  updateBeer_ShouldReturn403AndBeerNotUpdated() throws Exception {
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
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("Your user doesn't have permissions to perform this operation"));
    }


    @Test
    @WithMockUser(username = "2-Peter", roles = {"MANUFACTURER"})
    void  deleteBeer_ShouldReturn403AndBeerNotDeleted() throws Exception {
        ManufacturerEntity manufacturer = manufacturerRepository.findAll().getFirst();
        BeerEntity beer = new BeerEntity(null,"Turia",4.5,"Roast","Roast Spanish Beer",manufacturer);
        Long beerId = beerRepository.save(beer).getId();

        mock.perform(delete("/api/beers/{id}",beerId))
                .andExpect(status().isForbidden());
        mock.perform(get("/api/beers/{id}",beerId))
                .andExpect(status().isOk());
    }
}
