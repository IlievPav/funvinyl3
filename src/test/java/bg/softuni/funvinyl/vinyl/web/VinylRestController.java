package bg.softuni.funvinyl.vinyl.web;

import bg.softuni.funvinyl.repository.CategoryRepository;
import bg.softuni.funvinyl.repository.UserRepository;
import bg.softuni.funvinyl.repository.UserRoleRepository;
import bg.softuni.funvinyl.repository.VinylRepository;
import bg.softuni.funvinyl.service.CloudinaryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class VinylRestController {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private VinylRepository vinylRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CloudinaryService cloudinaryService;

    private VinylTestData testData;

    @BeforeEach
    public void setUp() {
        testData = new VinylTestData(
                userRepository, userRoleRepository,
                vinylRepository,
                categoryRepository,
                cloudinaryService
        );
        testData.init();
    }

    @AfterEach
    public void tearDown() {
        testData.cleanUp();
    }

/*    @Test
    @WithMockUser(value = "gosho", roles = {"USER"})
    void testFetchVinyls() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/vinyls/api")).
                andExpect(status().isOk())
                .andExpect(jsonPath("[0].artist").value("Scream"))
                .andExpect(jsonPath("[0].title").value("home"))
                .andExpect(jsonPath("[0].price").value(10))
                .andExpect(jsonPath("[0].description").value("home"))
                .andExpect(jsonPath("[0].category").value(""))
                .andExpect(jsonPath("[0].imageUrl").value("https://res.cloudinary.com/dr3m6za90/image/upload/v1617043753/glncm8wmqwwrulxxoqk1.jpg"))
                .andExpect(jsonPath("[0].contact").value("Sofia"))
                .andExpect(jsonPath("[0].created").value(LocalDateTime.of(2020, 3, 20, 8, 20)))
                .andExpect(jsonPath("[1].artist").value("Benga"))
                .andExpect(jsonPath("[1].title").value("Fire"))
                .andExpect(jsonPath("[1].price").value(12))
                .andExpect(jsonPath("[1].category").value("dubstep"))
                .andExpect(jsonPath("[1].description").value("gogo"))
                .andExpect(jsonPath("[1].imageUrl").value("https://res.cloudinary.com/dr3m6za90/image/upload/v1617043753/glncm8wmqwwrulxxoqk1.jpg"))
                .andExpect(jsonPath("[1].contact").value("Sofia"))
                .andExpect(jsonPath("[1].created").value(LocalDateTime.of(2020, 3, 20, 5, 20)));
    }
*/
}
