package bg.softuni.funvinyl.service;

import bg.softuni.funvinyl.domain.entities.CategoryEntity;
import bg.softuni.funvinyl.domain.models.binding.AddCategoryBindingModel;
import bg.softuni.funvinyl.domain.models.service.CategoryServiceModel;

import java.util.List;

public interface CategoryService {

    void seedCategories();

    List<CategoryServiceModel> findAllCategories();

    CategoryEntity findCategoryEntity(AddCategoryBindingModel addCategoryBindingModel);

    void createCategory(AddCategoryBindingModel addCategoryBindingModel);


}

