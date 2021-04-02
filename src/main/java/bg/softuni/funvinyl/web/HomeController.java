package bg.softuni.funvinyl.web;

import bg.softuni.funvinyl.domain.models.view.VinylViewModel;
import bg.softuni.funvinyl.service.VinylService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final VinylService vinylService;
    private final ModelMapper modelMapper;

    public HomeController(VinylService vinylService, ModelMapper modelMapper) {
        this.vinylService = vinylService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model, Principal principal){
        model.addAttribute("vinyls",
                this.vinylService.findLast3()
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

        return "home";
    }

    @GetMapping("/about")
    public String about(){
        return "about";
    }
}
