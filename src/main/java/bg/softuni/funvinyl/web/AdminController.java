package bg.softuni.funvinyl.web;

import bg.softuni.funvinyl.domain.models.binding.AddCategoryBindingModel;
import bg.softuni.funvinyl.service.CategoryService;
import bg.softuni.funvinyl.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final CategoryService categoryService;


    public AdminController(CategoryService categoryService) {
        this.categoryService = categoryService;

    }


    @ModelAttribute("category")
    public AddCategoryBindingModel createCategoryBindingModel() {
        return new AddCategoryBindingModel();
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("allCategories", categoryService.findAllCategories());

        return "/admin/add-category";

    }


    @PostMapping("/add")
    public String addCategory(@Valid AddCategoryBindingModel addCategoryBindingModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("category", addCategoryBindingModel);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.category", bindingResult);
            return "redirect:add";
        }

        categoryService.createCategory(addCategoryBindingModel);

        return "redirect:add";
    }


}
