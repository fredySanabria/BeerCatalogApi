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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * This class test filer and pagination features, I take the data from embedded database
 * 20 beers , 20 manufacturers
 */
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class AdvanceBeerSearchTest {
    @Autowired
    private MockMvc mock;


    @Test
    void  getListBeer_ShouldReturn200AndOneBeerFound() throws Exception {
        mock.perform(get("/api/beers?name=weiss&type=wheat&country=germany&page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void  getListBeer_ShouldReturn200AndTwoBeersFound() throws Exception {
        mock.perform(get("/api/beers?country=germany&page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void  getListBeer_ShouldReturn200And10BeersFoundFilterByABV() throws Exception {
        /* Found 20 but just show 20 due Pagination */
        mock.perform(get("/api/beers?ABVMin=4.5&ABVMax=6.0&page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(10)));
    }
    @Test
    void  getListBeer_ShouldReturn200And10BeersFoundFilterByABVSecondPage() throws Exception {
        /* Found 20 but just show 6 due Pagination */
        mock.perform(get("/api/beers?ABVMin=4.5&ABVMax=6.0&page=1&size=10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(6)));
    }
    @Test
    void  getListBeer_ShouldReturn200AndGetAllBeers() throws Exception {
        /* Found 20 but just show 20 due Pagination */
        mock.perform(get("/api/beers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(20)));
    }

    @Test
    void getListBeer_ShouldReturn200AndFilterByABVJustWithMinValue() throws Exception {
        /* Found 20 but just show 6 due Pagination */
        mock.perform(get("/api/beers?ABVMin=4.5&page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(10)));
    }
    @Test
    void getListBeer_ShouldReturn200AndFilterByABVJustWithMaxValue() throws Exception {
        /* Found 20 but just show 6 due Pagination */
        mock.perform(get("/api/beers?ABVMax=6.0&page=1&size=10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(10)));
    }

    @Test
    void getListBeer_ShouldReturn200AndSortedByName() throws Exception {
        /* Found 20 but just show 6 due Pagination */
        mock.perform(get("/api/beers?ABVMin=4.5&ABVMax=6.0&page=0&size=10&sort=name,asc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Asahi Super Dry"));
    }
    @Test
    void getListBeer_ShouldReturn200AndSortedByNameDesc() throws Exception {
        /* Found 20 but just show 6 due Pagination */
        mock.perform(get("/api/beers?ABVMin=4.5&ABVMax=6.0&page=0&size=10&sort=name,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Tiger Lager"));
    }
}
