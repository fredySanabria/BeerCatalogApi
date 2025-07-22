package beer.catalog.api.infrastructure;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * This class test filer and pagination features, I take the data from embedded database
 * 20 Manufacturers , 20 manufacturers
 */
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class AdvanceManufacturerSearchTest {
    @Autowired
    private MockMvc mock;


    @Test
    void  getListManufacturer_ShouldReturn200AndOneManufacturerFound() throws Exception {
        mock.perform(get("/api/manufacturers?name=Brahma&country=Brazil"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void  getListManufacturer_ShouldReturn200AndTwoManufacturersFound() throws Exception {
        mock.perform(get("/api/manufacturers?country=germany"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void  getListManufacturer_ShouldReturn200AndGetAllManufacturersNoPagination() throws Exception {
        /* Found 20 and show 20 without Pagination */
        mock.perform(get("/api/manufacturers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(20)));
    }

    @Test
    void  getListManufacturer_ShouldReturn200AndGetAllManufacturersFirstPage() throws Exception {
        /* Found 20 and show 10 due Pagination */
        mock.perform(get("/api/manufacturers?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(10)));
    }

    @Test
    void  getListManufacturer_ShouldReturn200AndGetAllManufacturersSecondPage() throws Exception {
        /* Found 20 and show 10 due Pagination Page 2*/
        mock.perform(get("/api/manufacturers?page=1&size=10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(10)));
    }

    @Test
    void getListManufacturer_ShouldReturn200AndSortedByName() throws Exception {
        mock.perform(get("/api/manufacturers?page=0&size=10&sort=name,asc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Amstel"));
    }
    @Test
    void getListManufacturer_ShouldReturn200AndSortedByNameDesc() throws Exception {
        mock.perform(get("/api/manufacturers?page=0&size=10&sort=name,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Tiger Beer"));
    }
}
