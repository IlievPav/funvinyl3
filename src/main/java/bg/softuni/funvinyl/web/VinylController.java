package bg.softuni.funvinyl.web;


import bg.softuni.funvinyl.domain.entities.CategoryEntity;
import bg.softuni.funvinyl.domain.entities.CommentsEntity;
import bg.softuni.funvinyl.domain.entities.UserEntity;
import bg.softuni.funvinyl.domain.models.binding.AddCategoryBindingModel;
import bg.softuni.funvinyl.domain.models.binding.AddVinylBindingModel;
import bg.softuni.funvinyl.domain.models.service.CommentsServiceModel;
import bg.softuni.funvinyl.domain.models.service.VinylServiceModel;
import bg.softuni.funvinyl.domain.models.service.CategoryServiceModel;
import bg.softuni.funvinyl.domain.models.view.VinylDetailViewModel;
import bg.softuni.funvinyl.domain.models.view.VinylViewModel;
import bg.softuni.funvinyl.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/vinyls")
public class VinylController {

    private final UserFavoriteVinylsService userFavoriteVinylsService;
    private final UserService userService;
    private final VinylService vinylService;
    private final CategoryService categoryService;
    private final CommentService commentService;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;


    public VinylController(UserFavoriteVinylsService userFavoriteVinylsService, UserService userService, VinylService vinylService, CategoryService categoryService,
                           CommentService commentService, ModelMapper modelMapper,
                           CloudinaryService cloudinaryService) {
        this.userFavoriteVinylsService = userFavoriteVinylsService;

        this.userService = userService;
        this.vinylService = vinylService;
        this.categoryService = categoryService;
        this.commentService = commentService;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
    }

    @ModelAttribute("addVinylBindingModel")
    public AddVinylBindingModel createVinylBindingModel() {
        return new AddVinylBindingModel();
    }

    @ModelAttribute("category")
    public AddCategoryBindingModel createCategoryBindingModel() {
        return new AddCategoryBindingModel();
    }


    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("allCategories", categoryService.findAllCategories());

        return "/vinyl/add-vinyl";

    }

    @PostMapping("/add")
    public String addVinylConfirm(@Valid AddVinylBindingModel addVinylBindingModel,
                                  AddCategoryBindingModel addCategoryBindingModel, BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes, Principal principal) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addVinylBindingModel", addVinylBindingModel);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.addVinylBindingModel", bindingResult);
            return "redirect:add";
        }

        VinylServiceModel vinylServiceModel = this.modelMapper.map(addVinylBindingModel, VinylServiceModel.class);

        CategoryEntity categoryEntity = categoryService.findCategoryEntity(addCategoryBindingModel);
        CategoryServiceModel categoryServiceModel = this.modelMapper.map(categoryEntity, CategoryServiceModel.class);

        String username = principal.getName();

        MultipartFile img = addVinylBindingModel.getImageUrl();
        String imageUrl = cloudinaryService.uploadImage(img);

        this.vinylService.createVinyl(vinylServiceModel, username, categoryServiceModel, imageUrl);

        return "redirect:/";
    }

    @GetMapping("/all")
    public String allVinyls(Model model, Principal principal) {

        model.addAttribute("vinyls",
                this.vinylService.findAllVinyls()
                        .stream()
                        .map(v -> {
                            VinylViewModel vinylViewModel = this.modelMapper.map(v, VinylViewModel.class);
                            vinylViewModel.setCategories(v.getCategories().getCategory());
                            String username = principal.getName();
                            vinylViewModel.setOwner(username.equals(vinylService.findById(v.getId()).getUser().getUsername()));
                           vinylViewModel.setUsername(vinylService.findById(v.getId()).getUser().getUsername());
                            return vinylViewModel;
                        })
                        .collect(Collectors.toList()));

        return "/vinyl/all-vinyls";
    }

    @GetMapping("/detail/{id}")
    public String details(@PathVariable("id") String id, Model model, Principal principal) {

        VinylServiceModel vinylServiceModel = this.vinylService.getVinylById(id);
        VinylDetailViewModel vinylDetailViewModel = this.modelMapper.map(vinylServiceModel, VinylDetailViewModel.class);

        vinylDetailViewModel.setCategories(vinylServiceModel.getCategories().getCategory());
        vinylDetailViewModel.setUsername(vinylService.findById(id).getUser().getUsername());

        String username = principal.getName();
        if (username.equals(vinylDetailViewModel.getUsername())) {
            model.addAttribute("owner", true);
        }
        model.addAttribute("vinyl", vinylDetailViewModel);

        return "/vinyl/details";
    }


    @GetMapping("/edit/{id}")
    public String editVinyl(@PathVariable String id, Model model) {
        VinylServiceModel vinylServiceModel = this.vinylService.getVinylById(id);
        AddVinylBindingModel addVinylBindingModel = this.modelMapper
                .map(vinylServiceModel, AddVinylBindingModel.class);

        model.addAttribute("image", vinylServiceModel.getImageUrl());
        model.addAttribute("allCategories", categoryService.findAllCategories());
        model.addAttribute("vinyl", addVinylBindingModel);
        model.addAttribute("vinylId", id);

        return "/vinyl/edit-vinyl";
    }


    @PostMapping("/edit/{id}")
    public String editVinylConfirm(@PathVariable String id, AddVinylBindingModel addVinylBindingModel,
                                   AddCategoryBindingModel addCategoryBindingModel) throws IOException {
        this.vinylService.checkCategoryAndImageEdit(id, addVinylBindingModel, addCategoryBindingModel);
        return "redirect:/vinyls/detail/" + id;
    }

    @GetMapping("/cancel/{id}")
    public String cancelVinylConfirm(@PathVariable String id) {
        return "redirect:/vinyls/detail/" + id;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) throws IOException {
        this.vinylService.delete(id);
        return "redirect:/";
    }

    @GetMapping("/my")
    public String myVinyls(Model model, Principal principal) {

        model.addAttribute("vinyls",
                this.vinylService.findAllVinylsByUser(principal.getName())
                        .stream()
                        .map(v -> {
                            VinylViewModel vinylViewModel = this.modelMapper.map(v, VinylViewModel.class);
                            vinylViewModel.setCategories(v.getCategories().getCategory());
                            String username = principal.getName();
                            vinylViewModel.setOwner(username.equals(vinylService.findById(v.getId()).getUser().getUsername()));
                           vinylViewModel.setUsername(vinylService.findById(v.getId()).getUser().getUsername());
                            return vinylViewModel;
                        })
                        .collect(Collectors.toList()));

        return "/vinyl/my-vinyls";
    }

    @GetMapping("/favorites/{id}")
    public String addToFavorites(@PathVariable("id") String id, Principal principal) {
        this.userFavoriteVinylsService.addToFavorites(id, principal.getName());

        return "redirect:/vinyls/detail/" + id;
      /*  return "/vinyl/my-favorites";*/
    }


    @GetMapping("/favorites")
    public String favorites(Model model, Principal principal){
        Optional<UserEntity> user = userService.findByUser(principal.getName());
        List<String> allFavorites = userFavoriteVinylsService.getAllFavorites(user.get().getId());

        List<VinylViewModel> collect = allFavorites.stream().map(f -> {

            VinylServiceModel vinylServiceModel = this.vinylService.getVinylById(f);
            VinylViewModel vinylViewModel = this.modelMapper.map(vinylServiceModel, VinylViewModel.class);

            vinylViewModel.setCategories(vinylServiceModel.getCategories().getCategory());
            vinylViewModel.setOwner(false);
            vinylViewModel.setUsername(vinylService.findById(f).getUser().getUsername());

            return vinylViewModel;
        }).collect(Collectors.toList());

        model.addAttribute("vinyls", collect);

        return "/vinyl/my-favorites";
    }


}
