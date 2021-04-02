package bg.softuni.funvinyl.vinyl.web;

import bg.softuni.funvinyl.domain.entities.CategoryEntity;
import bg.softuni.funvinyl.domain.entities.UserEntity;
import bg.softuni.funvinyl.domain.entities.UserRoleEntity;
import bg.softuni.funvinyl.domain.entities.VinylsEntity;
import bg.softuni.funvinyl.domain.entities.enums.UserRoleEnum;
import bg.softuni.funvinyl.domain.models.binding.AddCategoryBindingModel;
import bg.softuni.funvinyl.domain.models.service.CategoryServiceModel;
import bg.softuni.funvinyl.domain.models.service.VinylServiceModel;
import bg.softuni.funvinyl.domain.models.view.VinylDetailViewModel;
import bg.softuni.funvinyl.domain.models.view.VinylViewModel;
import bg.softuni.funvinyl.repository.CategoryRepository;
import bg.softuni.funvinyl.repository.UserRepository;
import bg.softuni.funvinyl.repository.UserRoleRepository;
import bg.softuni.funvinyl.repository.VinylRepository;
import bg.softuni.funvinyl.service.CloudinaryService;
import bg.softuni.funvinyl.service.VinylService;
import bg.softuni.funvinyl.service.impl.VinylUserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class VinylControllerTest {

    private static final String VINYL_CONTROLLER_PREFIX = "/vinyls";

    private String testVinylId;
    @MockBean
    CloudinaryService mockCloudinaryService;
    @MockBean
    private VinylService serviceToTest;

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
    @MockBean
    private ModelMapper modelMapper;



    @BeforeEach
    public void setUp() throws IOException {
      /*  testData = new VinylTestData(userRepository, vinylRepository,
                categoryRepository, mockCloudinaryService);*/
        modelMapper = new ModelMapper();
        VinylServiceModel vinylServiceModel = new VinylServiceModel();

        VinylsEntity init = init();

        when(mockCloudinaryService.uploadImage(Mockito.any())).thenReturn("https://res.cloudinary.com/dr3m6za90/image/upload/v1617043753/glncm8wmqwwrulxxoqk1.jpg");
        when( serviceToTest.findById(Mockito.any())).thenReturn(init);
        when( serviceToTest.getVinylById(Mockito.any())).thenReturn(vinylServiceModel);



        //when(serviceToTest.findById(testVinylId)).thenReturn(Optional.of(gosho));
      //when(mockVinylRepository.findById(testVinylId)).thenReturn(Optional.of(gosh));
        /*   testData.init();
        testVinylId = testData.getTestVinylId();*/
    }




    private UserEntity initUser() {
        UserEntity gosho = new UserEntity();

        gosho.setUsername("gosho");
        gosho.setPassword("123");
        gosho.setEmail("gosho@gmail.com");
        gosho.setFullName("goshev");
        UserRoleEntity roleUser = new UserRoleEntity();
        roleUser.setRole(UserRoleEnum.USER);
        roleUser = userRoleRepository.saveAndFlush(roleUser);
        gosho.setRoles(List.of(roleUser));
        gosho = userRepository.saveAndFlush(gosho);

      return gosho;    }
    private VinylsEntity init() {
        UserEntity userEntity = initUser();
        VinylsEntity scream = new VinylsEntity();

        scream.setArtist("Scream");
        scream.setTitle("home");
        scream.setDescription("go home");
        scream.setPrice(BigDecimal.valueOf(10));
        scream.setImageUrl("https://res.cloudinary.com/dr3m6za90/image/upload/v1617043753/glncm8wmqwwrulxxoqk1.jpg");
        scream.setContact("Sofia");
        scream.setCreated(LocalDateTime.of(2020, 3,20, 8,20));
        CategoryEntity categoryGrime = new CategoryEntity();
        categoryGrime.setCategoryName("grime");
        categoryGrime= categoryRepository.saveAndFlush(categoryGrime);
        scream.setCategory(categoryGrime);
        scream.setUser(userEntity);

        scream= vinylRepository.saveAndFlush(scream);
        testVinylId = scream.getId();
        return scream;
    }

/*    @Test
    @WithMockUser(value = "gosho", roles = {"USER"})
    void testShouldReturnValidStatusViewNameAndModel() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(
                VINYL_CONTROLLER_PREFIX + "/detail/{id}", testVinylId
        )).andExpect(status().isOk())
                .andExpect(view().name("/vinyl/details"))
                .andExpect(model().attributeExists("vinyl"))
                .andExpect(model().attributeExists("owner"));
    }*/

    void cleanUp() {
        userRoleRepository.deleteAll();
        categoryRepository.deleteAll();
        vinylRepository.deleteAll();
        userRepository.deleteAll();
    }
}
