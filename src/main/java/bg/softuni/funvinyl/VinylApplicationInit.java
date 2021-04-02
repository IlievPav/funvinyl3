package bg.softuni.funvinyl;

import bg.softuni.funvinyl.service.CategoryService;
import bg.softuni.funvinyl.service.UserService;
import bg.softuni.funvinyl.service.VinylService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class VinylApplicationInit implements CommandLineRunner {


    private final UserService userService;
    private final CategoryService categoryService;

    public VinylApplicationInit(UserService userService, CategoryService categoryService) {
        this.userService = userService;

        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        userService.seedUsers();
        categoryService.seedCategories();

    }
}
