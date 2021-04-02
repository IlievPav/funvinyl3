package bg.softuni.funvinyl.service.impl;

import bg.softuni.funvinyl.domain.entities.CategoryEntity;

import bg.softuni.funvinyl.domain.entities.CommentsEntity;
import bg.softuni.funvinyl.domain.entities.UserEntity;
import bg.softuni.funvinyl.domain.entities.VinylsEntity;
import bg.softuni.funvinyl.domain.models.binding.AddCategoryBindingModel;
import bg.softuni.funvinyl.domain.models.binding.AddVinylBindingModel;
import bg.softuni.funvinyl.domain.models.service.CommentsServiceModel;
import bg.softuni.funvinyl.domain.models.service.VinylServiceModel;
import bg.softuni.funvinyl.domain.models.service.CategoryServiceModel;
import bg.softuni.funvinyl.repository.CategoryRepository;
import bg.softuni.funvinyl.repository.VinylRepository;
import bg.softuni.funvinyl.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
public class VinylServiceImpl implements VinylService {

    private final UserService userService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;
    private final VinylRepository vinylRepository;
    private final CloudinaryService cloudinaryService;
    private final CommentService commentService;
    private final CategoryRepository categoryRepository;



    public VinylServiceImpl(UserService userService, CategoryService categoryService, ModelMapper modelMapper,
                            VinylRepository vinylRepository, CloudinaryService cloudinaryService, CommentService commentService,
                            CategoryRepository categoryRepository) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
        this.vinylRepository = vinylRepository;
        this.cloudinaryService = cloudinaryService;
        this.commentService = commentService;
        this.categoryRepository = categoryRepository;

    }

    @Override
    @Transactional
    public VinylServiceModel createVinyl(VinylServiceModel vinylServiceModel, String username,
                                         CategoryServiceModel categoryServiceModel,
                                         String imageUrl) {
        VinylsEntity vinylsEntity = this.modelMapper.map(vinylServiceModel, VinylsEntity.class);
        vinylsEntity.setImageUrl(imageUrl);

        CategoryEntity categoryEntity = modelMapper.map(categoryServiceModel, CategoryEntity.class);
        vinylsEntity.setCategory(categoryEntity);

        CommentsEntity emptyCommentsEntities = this.commentService.createEmptyCommentsEntities();
        List<CommentsEntity> commentsEntities = new ArrayList<>();
        commentsEntities.add(emptyCommentsEntities);
        vinylsEntity.setComments(commentsEntities);

        CommentsServiceModel commentsServiceModel = this.modelMapper.map(emptyCommentsEntities, CommentsServiceModel.class);
        List<CommentsServiceModel> commentsServiceModelList = new ArrayList<>();
        commentsServiceModelList.add(commentsServiceModel);
        vinylServiceModel.setComments(commentsServiceModelList);

        UserEntity creator = userService.findByUser(username)
                .orElseThrow(() -> new IllegalArgumentException("Creator " + username + " could not be found"));
        vinylsEntity.setUser(creator);

        vinylsEntity = this.vinylRepository.saveAndFlush(vinylsEntity);

        return this.modelMapper.map(vinylsEntity, VinylServiceModel.class);
    }


    @Override
    @Transactional
    public List<VinylServiceModel> findAllVinyls() {
        List<VinylsEntity> allVinyls = this.vinylRepository.findAllByOrderByCreatedDesc();

        return getVinylsServiceModels(allVinyls);
    }


    @Override
    @Transactional
    public List<VinylServiceModel> findLast3() {
        List<VinylsEntity> allVinyls = this.vinylRepository.findTop3ByOrderByCreatedDesc();

        System.out.println();
        return getVinylsServiceModels(allVinyls);
    }

    @Override
    public VinylServiceModel getVinylById(String id) {

        VinylServiceModel vinylServiceModel1 = this.vinylRepository.findById(id)
                .map(v -> {
                    List<CommentsEntity> commentsByVinylId1 = this.commentService.findCommentsByVinylId(id);
                    v.setComments(commentsByVinylId1);
                    VinylServiceModel vinylServiceModel = this.modelMapper.map(v, VinylServiceModel.class);

                    CategoryServiceModel categoryServiceModel = new CategoryServiceModel();
                    categoryServiceModel.setCategory(v.getCategory().getCategoryName());
                    vinylServiceModel.setCategories(categoryServiceModel);

                    List<CommentsEntity> commentsByVinylId = commentService.findCommentsByVinylId(v.getId());
                    List<CommentsServiceModel> commentsService = new ArrayList<>();
                    vinylServiceModel.setComments(commentsService);

                    vinylServiceModel.setImageUrl(v.getImageUrl());
                    vinylServiceModel.setContact(v.getContact());

                    return vinylServiceModel;
                }).orElse(null);

        return vinylServiceModel1;
    }

    @Override
    public VinylsEntity findById(String id) {
        return vinylRepository.findById(id).orElse(null);
    }


    @Override
    public void delete(String id) throws IOException {
        VinylsEntity vinyl = this.vinylRepository.findById(id).orElse(null);

        cloudinaryService.delete(vinyl.getImageUrl());
        this.vinylRepository.delete(vinyl);
    }

    @Override
    public VinylServiceModel editVinyl(String id, VinylServiceModel vinylServiceModel,
                                       CategoryServiceModel categoryServiceModel, String imageUrl) {

        VinylsEntity vinylsEntity = this.vinylRepository.findById(id).orElse(null);

        vinylsEntity.setArtist(vinylServiceModel.getArtist());
        vinylsEntity.setTitle(vinylServiceModel.getTitle());
        vinylsEntity.setPrice(vinylServiceModel.getPrice());
        vinylsEntity.prePersist();
        vinylsEntity.setDescription(vinylServiceModel.getDescription());
        vinylsEntity.setContact(vinylServiceModel.getContact());

        CategoryEntity categoryEntity = this.categoryRepository.findCategoryEntitiesByCategoryName(categoryServiceModel.getCategory());
        vinylsEntity.setCategory(categoryEntity);

        vinylsEntity.setImageUrl(imageUrl);

        return this.modelMapper.map(this.vinylRepository.saveAndFlush(vinylsEntity), VinylServiceModel.class);
    }

    @Override
    public void checkCategoryAndImageEdit(String id, AddVinylBindingModel addVinylBindingModel,
                                          AddCategoryBindingModel addCategoryBindingModel) throws IOException {

        if (addCategoryBindingModel.getCategory().isEmpty()) {
            VinylServiceModel byId = getVinylById(id);
            CategoryServiceModel categoryServiceModel = byId.getCategories();
            checkEditImage(id, addVinylBindingModel, categoryServiceModel);
        } else {
            CategoryServiceModel categoryServiceModel = this.modelMapper.map(addCategoryBindingModel, CategoryServiceModel.class);
            checkEditImage(id, addVinylBindingModel, categoryServiceModel);
        }
    }

    @Override
    public void checkEditImage(String id, AddVinylBindingModel addVinylBindingModel,
                               CategoryServiceModel categoryServiceModel) throws IOException {
        if (addVinylBindingModel.getImageUrl().isEmpty()) {
            String imageUrl = getVinylById(id).getImageUrl();
            VinylServiceModel vinylServiceModel = this.modelMapper.map(addVinylBindingModel, VinylServiceModel.class);
            editVinyl(id, vinylServiceModel, categoryServiceModel, imageUrl);
        } else {
            MultipartFile img = addVinylBindingModel.getImageUrl();
            String imageUrl = cloudinaryService.uploadImage(img);
            VinylServiceModel vinylServiceModel = this.modelMapper.map(addVinylBindingModel, VinylServiceModel.class);
            editVinyl(id, vinylServiceModel, categoryServiceModel, imageUrl);
        }
    }

    @Override
    public List<VinylServiceModel> findAllVinylsByUser(String username) {
        System.out.println();
        Optional<UserEntity> byUser = userService.findByUser(username);
        List<VinylsEntity> vinylsEntityByUserId = vinylRepository.findVinylsEntityByUserId(byUser.get().getId());
        System.out.println();
        List<VinylServiceModel> collect = new ArrayList<>();
        for (VinylsEntity v : vinylsEntityByUserId) {
            VinylServiceModel vinylById = getVinylById(v.getId());
            collect.add(vinylById);
        }
        System.out.println();
        return collect;
    }


    private List<VinylServiceModel> getVinylsServiceModels(List<VinylsEntity> allVinyls) {
        List<VinylServiceModel> collect = allVinyls.stream()
                .map(v -> {
                    VinylServiceModel map = this.modelMapper.map(v, VinylServiceModel.class);
                    CategoryServiceModel categoryServiceModel = new CategoryServiceModel();
                    categoryServiceModel.setCategory(v.getCategory().getCategoryName());
                    map.setCategories(categoryServiceModel);
                    map.setImageUrl(v.getImageUrl());

                    return map;
                })
                .collect(Collectors.toList());
        System.out.println();
        return collect;
    }


}
