package bg.softuni.funvinyl.service;

import bg.softuni.funvinyl.domain.entities.CategoryEntity;
import bg.softuni.funvinyl.domain.entities.VinylsEntity;
import bg.softuni.funvinyl.domain.models.binding.AddCategoryBindingModel;
import bg.softuni.funvinyl.domain.models.binding.AddVinylBindingModel;
import bg.softuni.funvinyl.domain.models.service.VinylServiceModel;
import bg.softuni.funvinyl.domain.models.service.CategoryServiceModel;
import bg.softuni.funvinyl.domain.models.view.VinylDetailViewModel;
import bg.softuni.funvinyl.domain.models.view.VinylViewModel;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public interface VinylService {
    VinylServiceModel createVinyl(VinylServiceModel vinylServiceModel,
                                  String username, CategoryServiceModel categoryServiceModel, String imageUrl) ;

    List<VinylServiceModel> findAllVinyls();

    List<VinylServiceModel> findLast3();

    VinylServiceModel getVinylById(String id);

    VinylsEntity findById(String id);

    void delete(String id) throws IOException;

    VinylServiceModel editVinyl(String id, VinylServiceModel vinylServiceModel,
                                CategoryServiceModel categoryServiceModel, String imageUrl);


    void checkCategoryAndImageEdit(String id, AddVinylBindingModel addVinylBindingModel,
                                   AddCategoryBindingModel addCategoryBindingModel) throws IOException;

    void checkEditImage(String id, AddVinylBindingModel addVinylBindingModel,
                        CategoryServiceModel categoryServiceModel) throws IOException;

    List<VinylServiceModel> findAllVinylsByUser(String username);
    /* List<VinylServiceModel>  findFavorites();*/


}
