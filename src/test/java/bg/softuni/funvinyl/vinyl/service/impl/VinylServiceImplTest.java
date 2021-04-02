package bg.softuni.funvinyl.vinyl.service.impl;

import bg.softuni.funvinyl.domain.entities.CategoryEntity;
import bg.softuni.funvinyl.domain.entities.UserEntity;
import bg.softuni.funvinyl.domain.entities.VinylsEntity;
import bg.softuni.funvinyl.domain.models.service.VinylServiceModel;
import bg.softuni.funvinyl.repository.CategoryRepository;
import bg.softuni.funvinyl.repository.VinylRepository;
import bg.softuni.funvinyl.service.CategoryService;
import bg.softuni.funvinyl.service.CloudinaryService;
import bg.softuni.funvinyl.service.CommentService;
import bg.softuni.funvinyl.service.UserService;
import bg.softuni.funvinyl.service.impl.VinylServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VinylServiceImplTest {

    private UserEntity userTest1, userTest2;
    private VinylsEntity vinylTest1, vinylTest2;

    private VinylServiceImpl vinylServiceToTest;

    @Mock
    VinylRepository mockVinylRepository;
    @Mock
    CategoryRepository mockCategoryRepository;
    @Mock
    UserService userService;
    @Mock
    CategoryService categoryService;
    @Mock
    ModelMapper modelMapper;
    @Mock
    CloudinaryService cloudinaryService;
    @Mock
    CommentService commentService;

    private List<VinylsEntity> vinylsEntityList;

    @BeforeEach
    public void init(){
        vinylsEntityList = new ArrayList<>();

        userTest1 = new UserEntity();
        userTest1.setUsername("user1");

        vinylTest1 = new VinylsEntity();
        vinylTest1.setArtist("Benga");
        vinylTest1.setTitle("Fire");
        vinylTest1.setDescription("gogo");
        vinylTest1.setPrice(BigDecimal.valueOf(12));
        vinylTest1.setImageUrl("https://res.cloudinary.com/dr3m6za90/image/upload/v1617043753/glncm8wmqwwrulxxoqk1.jpg");
        vinylTest1.setContact("Sofia");
        vinylTest1.setCreated(LocalDateTime.of(2020, 6,20, 5,20));
        CategoryEntity categoryDubstep = new CategoryEntity();
        categoryDubstep.setCategoryName("dubstep");
        vinylTest1.setCategory(categoryDubstep);
        vinylTest1.setUser(userTest1);
        vinylTest1.setId("5");


        userTest2 = new UserEntity();
        userTest2.setUsername("user2");

        vinylTest2 = new VinylsEntity();
        vinylTest2.setArtist("Scream");
        vinylTest2.setTitle("home");
        vinylTest2.setDescription("goHome");
        vinylTest2.setPrice(BigDecimal.valueOf(10));
        vinylTest2.setImageUrl("https://res.cloudinary.com/dr3m6za90/image/upload/v1617043753/glncm8wmqwwrulxxoqk1.jpg");
        vinylTest2.setContact("Pld");
        vinylTest2.setCreated(LocalDateTime.of(2020, 5,15, 5,20));
        CategoryEntity categoryGrime = new CategoryEntity();
        categoryDubstep.setCategoryName("grime");
        vinylTest2.setCategory(categoryGrime);
        vinylTest2.setUser(userTest2);

        vinylsEntityList.add(vinylTest1);
        vinylsEntityList.add(vinylTest2);

        vinylServiceToTest = new VinylServiceImpl(userService, categoryService,new ModelMapper(),
                mockVinylRepository, cloudinaryService,commentService, mockCategoryRepository);
    }
    @Test
    public void testFindAllVinyls() {
        when(mockVinylRepository.findAllByOrderByCreatedDesc()).thenReturn(vinylsEntityList);
        List<VinylServiceModel> allVinyls = vinylServiceToTest.findAllVinyls();

        Assertions.assertEquals(2, allVinyls.size());
        VinylServiceModel model1 = allVinyls.get(0);
        VinylServiceModel model2 = allVinyls.get(1);

        Assertions.assertEquals(vinylTest1.getArtist(), model1.getArtist());
        Assertions.assertEquals(vinylTest1.getTitle(), model1.getTitle());
        Assertions.assertEquals(vinylTest1.getCategory().getCategoryName(), model1.getCategories().getCategory());
        Assertions.assertEquals(vinylTest1.getImageUrl(), model1.getImageUrl());
        Assertions.assertEquals(vinylTest1.getComments(), model1.getComments());
        Assertions.assertEquals(vinylTest1.getContact(), model1.getContact());
        Assertions.assertEquals(vinylTest1.getDescription(), model1.getDescription());
        Assertions.assertEquals(vinylTest1.getPrice(), model1.getPrice());

        Assertions.assertEquals(vinylTest2.getArtist(), model2.getArtist());
        Assertions.assertEquals(vinylTest2.getTitle(), model2.getTitle());
        Assertions.assertEquals(vinylTest2.getCategory().getCategoryName(), model2.getCategories().getCategory());
        Assertions.assertEquals(vinylTest2.getImageUrl(), model2.getImageUrl());
        Assertions.assertEquals(vinylTest2.getComments(), model2.getComments());
        Assertions.assertEquals(vinylTest2.getContact(), model2.getContact());
        Assertions.assertEquals(vinylTest2.getDescription(), model2.getDescription());
        Assertions.assertEquals(vinylTest2.getPrice(), model2.getPrice());

    }

    @Test
    public void testFindLast3() {
        when(mockVinylRepository.findTop3ByOrderByCreatedDesc()).thenReturn(vinylsEntityList);
        List<VinylServiceModel> last3Vinyls = vinylServiceToTest.findLast3();

        Assertions.assertEquals(2, last3Vinyls.size());
        VinylServiceModel model3 = last3Vinyls.get(0);
        VinylServiceModel model4 = last3Vinyls.get(1);

        Assertions.assertEquals(vinylTest1.getArtist(), model3.getArtist());
        Assertions.assertEquals(vinylTest1.getTitle(), model3.getTitle());
        Assertions.assertEquals(vinylTest1.getCategory().getCategoryName(), model3.getCategories().getCategory());
        Assertions.assertEquals(vinylTest1.getImageUrl(), model3.getImageUrl());
        Assertions.assertEquals(vinylTest1.getComments(), model3.getComments());
        Assertions.assertEquals(vinylTest1.getContact(), model3.getContact());
        Assertions.assertEquals(vinylTest1.getDescription(), model3.getDescription());
        Assertions.assertEquals(vinylTest1.getPrice(), model3.getPrice());

        Assertions.assertEquals(vinylTest2.getArtist(), model4.getArtist());
        Assertions.assertEquals(vinylTest2.getTitle(), model4.getTitle());
        Assertions.assertEquals(vinylTest2.getCategory().getCategoryName(), model4.getCategories().getCategory());
        Assertions.assertEquals(vinylTest2.getImageUrl(), model4.getImageUrl());
        Assertions.assertEquals(vinylTest2.getComments(), model4.getComments());
        Assertions.assertEquals(vinylTest2.getContact(), model4.getContact());
        Assertions.assertEquals(vinylTest2.getDescription(), model4.getDescription());
        Assertions.assertEquals(vinylTest2.getPrice(), model4.getPrice());

    }


    @Test
    public void testGetVinylById() {
        when(mockVinylRepository.findById("5")).thenReturn(java.util.Optional.ofNullable(vinylTest1));
        VinylServiceModel vinylByID = vinylServiceToTest.getVinylById(vinylTest1.getId());

        VinylServiceModel model5 = vinylByID;
        Assertions.assertEquals(vinylTest1.getArtist(), model5.getArtist());
        Assertions.assertEquals(vinylTest1.getTitle(), model5.getTitle());
        Assertions.assertEquals(vinylTest1.getCategory().getCategoryName(), model5.getCategories().getCategory());
        Assertions.assertEquals(vinylTest1.getImageUrl(), model5.getImageUrl());
        Assertions.assertEquals(vinylTest1.getComments(), model5.getComments());
        Assertions.assertEquals(vinylTest1.getContact(), model5.getContact());
        Assertions.assertEquals(vinylTest1.getDescription(), model5.getDescription());
        Assertions.assertEquals(vinylTest1.getPrice(), model5.getPrice());
        Assertions.assertEquals(vinylTest1.getId(), model5.getId());

    }


}