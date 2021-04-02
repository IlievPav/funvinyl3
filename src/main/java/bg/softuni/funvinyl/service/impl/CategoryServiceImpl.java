package bg.softuni.funvinyl.service.impl;

import bg.softuni.funvinyl.domain.entities.CategoryEntity;
import bg.softuni.funvinyl.domain.models.binding.AddCategoryBindingModel;
import bg.softuni.funvinyl.domain.models.service.CategoryServiceModel;
import bg.softuni.funvinyl.repository.CategoryRepository;
import bg.softuni.funvinyl.service.CategoryService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final static String CATEGORY = "src/main/resources/json/category.json";

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper, Gson gson) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public void seedCategories() {
        if (categoryRepository.count() == 0) {
            String content = null;
            try {
                content = (String.join("", Files.readAllLines(Path.of(CATEGORY))));
                CategoryEntity[] categoryEntities = this.gson.fromJson(content, CategoryEntity[].class);

                for (CategoryEntity categoryEntity : categoryEntities) {
                    this.categoryRepository.saveAndFlush(categoryEntity);
                }


            } catch (IOException e) {
                throw new IllegalStateException("Cannot seed categories!");
            }
        }
          /*  CategoryEntity hp = new CategoryEntity();
            hp.setCategoryName("Hip Hop");
            CategoryEntity hc = new CategoryEntity();
            hc.setCategoryName("Hardcore");
            categoryRepository.saveAll(List.of(hp,hc));*/

    }

    @Override
    public List<CategoryServiceModel> findAllCategories() {
        return this.categoryRepository.findAllOrdered()
                .stream()
                .map(c -> this.modelMapper.map(c, CategoryServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryEntity findCategoryEntity(AddCategoryBindingModel addCategoryBindingModel) {
        CategoryEntity category = categoryRepository.
                findCategoryEntitiesByCategoryName(addCategoryBindingModel.getCategory());

        return category;
    }

    @Override
    public void createCategory(AddCategoryBindingModel addCategoryBindingModel) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryName(addCategoryBindingModel.getCategory());

        categoryRepository.saveAndFlush(categoryEntity);
    }


}
