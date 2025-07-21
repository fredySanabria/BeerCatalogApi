package beer.catalog.api.infrastructure;

import beer.catalog.api.infrastructure.persistence.entity.ManufacturerEntity;
import beer.catalog.api.infrastructure.persistence.repository.ManufacturerJPARepository;
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
public class ManufacturerControllerIntegrationTest {
    @Autowired
    private MockMvc mock;

    @Autowired
    private ManufacturerJPARepository manufacturerRepository;


    @Test
    void  createManufacturer_ShouldReturn201AndManufacturerCreated() throws Exception {
        String json = """
    {
        "name": "Artisanal Manufacturer",
        "country": "local"
    }
       \s""";

        mock.perform(post("/api/manufacturers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Artisanal Manufacturer"));
    }

    @Test
    void  createManufacturer_ShouldReturn400AndManufacturerNotCreated() throws Exception {
        String json = """
    {
        "name": "7",
        "country": "local create"
    }
       \s""";

        mock.perform(post("/api/manufacturers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation errors"))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0]").value("name: The name can only contain letters and spaces"));
    }

    @Test
    void  getManufacturer_ShouldReturn200AndManufacturerFound() throws Exception {
        ManufacturerEntity manufacturer = new ManufacturerEntity(null,"Artisanal Manufacturer","local get");
        Long manufacturerId = manufacturerRepository.save(manufacturer).getId();

        mock.perform(get("/api/manufacturers/{id}",manufacturerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Artisanal Manufacturer"))
                .andExpect(jsonPath("$.country").value("local get"));
    }
    @Test
    void  getManufacturer_ShouldReturn404AndManufacturerNotFound() throws Exception {
        Long manufacturerId = 7L;
        mock.perform(get("/api/manufacturers/{id}",manufacturerId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Beer Manufacturer with id 7 not found"));
    }

    @Test
    void  getListManufacturer_ShouldReturn200AndManufacturerArrayFound() throws Exception {
        mock.perform(get("/api/manufacturers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void  updateManufacturer_ShouldReturn200AndManufacturerUpdated() throws Exception {
        ManufacturerEntity Manufacturer = new ManufacturerEntity(null,"Artisanal Manufacturer","local update");
        Long manufacturerId = manufacturerRepository.save(Manufacturer).getId();
        String json = """
    {
        "id" : %d,
        "name": "other Manufacturer",
        "country": "local updated"
    }
       \s""".formatted(manufacturerId);

        mock.perform(put("/api/manufacturers/{id}",manufacturerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("other Manufacturer"))
                .andExpect(jsonPath("$.country").value("local updated"));
    }

    @Test
    void  updateManufacturer_ShouldReturn400AndManufacturerUpdated() throws Exception {
        ManufacturerEntity Manufacturer = new ManufacturerEntity(null,"Artisanal Manufacturer","local update");
        Long manufacturerId = manufacturerRepository.save(Manufacturer).getId();
        String json = """
    {
        "id" : "70",
        "name": "other Manufacturer",
        "country": "local updated"
    }
       \s""";

        mock.perform(put("/api/manufacturers/{id}",manufacturerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("400 BAD_REQUEST \"Manufacturer id is different from existing in the body\""));
    }

    @Test
    void  updateManufacturer_ShouldReturn400AndManufacturerNotUpdatedWhenNotValidData() throws Exception {
        ManufacturerEntity Manufacturer = new ManufacturerEntity(null,"Artisanal Manufacturer","local update");
        Long manufacturerId = manufacturerRepository.save(Manufacturer).getId();

        String json = """
    {
        "id" : %d,
        "name": "Artisanal Manufacturer",
        "country": 99
    }
       \s""".formatted(manufacturerId);

        mock.perform(put("/api/manufacturers/{id}",manufacturerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation errors"))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0]").value("country: The country can only contain letters and spaces"));
    }

    @Test
    void  updateManufacturer_ShouldReturn400AndManufacturerNotUpdatedWhenManufacturerNotFound() throws Exception {

        String json = """
    {
        "id" : %d,
        "name": "other Manufacturer",
        "country": "local updated"
    }
       \s""".formatted(70L);

        mock.perform(put("/api/manufacturers/{id}",70L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("Beer Manufacturer with id 70 not found"));
    }

    @Test
    void  deleteManufacturer_ShouldReturn204AndManufacturerDeleted() throws Exception {
        ManufacturerEntity manufacturer = new ManufacturerEntity(null,"Artisanal Manufacturer","local delete");
        Long manufacturerId = manufacturerRepository.save(manufacturer).getId();

        mock.perform(delete("/api/manufacturers/{id}",manufacturerId))
                .andExpect(status().isNoContent());
        mock.perform(get("/api/manufacturers/{id}",manufacturerId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Beer Manufacturer with id %d not found".formatted(manufacturerId)));
    }
    @Test
    void  deleteManufacturer_ShouldReturn404AndManufacturerNotFound() throws Exception {
        Long manufacturerId = 77L;
        mock.perform(delete("/api/manufacturers/{id}",manufacturerId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Beer Manufacturer with id %d not found".formatted(manufacturerId)));
    }

}
