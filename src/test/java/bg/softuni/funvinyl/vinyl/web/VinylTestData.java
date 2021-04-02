package bg.softuni.funvinyl.vinyl.web;

import bg.softuni.funvinyl.domain.entities.CategoryEntity;
import bg.softuni.funvinyl.domain.entities.UserEntity;
import bg.softuni.funvinyl.domain.entities.UserRoleEntity;
import bg.softuni.funvinyl.domain.entities.VinylsEntity;
import bg.softuni.funvinyl.domain.entities.enums.UserRoleEnum;
import bg.softuni.funvinyl.repository.CategoryRepository;
import bg.softuni.funvinyl.repository.UserRepository;
import bg.softuni.funvinyl.repository.UserRoleRepository;
import bg.softuni.funvinyl.repository.VinylRepository;
import bg.softuni.funvinyl.service.CloudinaryService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VinylTestData {

    private String testVinylId;

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private VinylRepository vinylRepository;
    private CategoryRepository categoryRepository;
    private CloudinaryService cloudinaryService;



    public VinylTestData(UserRepository userRepository,UserRoleRepository userRoleRepository,
                         VinylRepository vinylRepository,
                         CategoryRepository categoryRepository, CloudinaryService cloudinaryService) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.vinylRepository = vinylRepository;
        this.categoryRepository = categoryRepository;
        this.cloudinaryService = cloudinaryService;

    }

    public void init(){
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("gosho");
        userEntity.setPassword("12345");
        userEntity.setEmail("gosho@gmail.com");
        userEntity.setFullName("goshov");
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setRole(UserRoleEnum.USER);
        userRoleEntity = userRoleRepository.save(userRoleEntity);
        List<UserRoleEntity> userRoleEntityList = new ArrayList<>();
        userRoleEntityList.add(userRoleEntity);
        userEntity.setRoles(userRoleEntityList);
        userEntity = userRepository.save(userEntity);

        VinylsEntity benga = new VinylsEntity();
        benga.setArtist("Benga");
        benga.setTitle("Fire");
        benga.setDescription("gogo");
        benga.setPrice(BigDecimal.valueOf(12));
        benga.setImageUrl("https://res.cloudinary.com/dr3m6za90/image/upload/v1617043753/glncm8wmqwwrulxxoqk1.jpg");
        benga.setContact("Sofia");
        benga.setCreated(LocalDateTime.of(2020, 3,20, 5,20));
        CategoryEntity categoryDubstep = new CategoryEntity();
        categoryDubstep.setCategoryName("dubstep");
        categoryDubstep = categoryRepository.save(categoryDubstep);

        benga.setCategory(categoryDubstep);
        benga.setUser(userEntity);

        benga= vinylRepository.save(benga);

        VinylsEntity scream = new VinylsEntity();
        scream.setArtist("Scream");
        scream.setTitle("home");
        scream.setDescription("home");
        scream.setPrice(BigDecimal.valueOf(10));
        scream.setImageUrl("https://res.cloudinary.com/dr3m6za90/image/upload/v1617043753/glncm8wmqwwrulxxoqk1.jpg");
        scream.setContact("Sofia");
        benga.setCreated(LocalDateTime.of(2020, 3,20, 8,20));
        CategoryEntity categoryGrime = new CategoryEntity();
        categoryGrime.setCategoryName("grime");
        categoryGrime = categoryRepository.save(categoryGrime);
        scream.setCategory(categoryGrime);
        scream.setUser(userEntity);

        scream= vinylRepository.save(scream);
    }

    void cleanUp(){
        vinylRepository.deleteAll();
        categoryRepository.deleteAll();
        userRepository.deleteAll();
    }

    public String getTestVinylId(){return testVinylId;}
}
