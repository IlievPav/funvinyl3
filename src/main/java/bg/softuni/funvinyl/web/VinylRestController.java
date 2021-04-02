package bg.softuni.funvinyl.web;

import bg.softuni.funvinyl.domain.models.view.VinylViewModel;
import bg.softuni.funvinyl.service.VinylService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/vinyls")
@RestController
public class VinylRestController {
    private final ModelMapper modelMapper;
    private final VinylService vinylService;



    public VinylRestController(ModelMapper modelMapper, VinylService vinylService) {
        this.modelMapper = modelMapper;
        this.vinylService = vinylService;

    }

    @GetMapping("/api")
    public ResponseEntity<List<VinylViewModel>> findAll(Principal principal){
        return ResponseEntity
                .ok()
                .body(this.vinylService.findAllVinyls()
                        .stream()
                        .map(v -> {
                            VinylViewModel vinylViewModel = this.modelMapper.map(v, VinylViewModel.class);
                            vinylViewModel.setCategories(v.getCategories().getCategory());
                            String username = principal.getName();
                            vinylViewModel.setOwner(username.equals(vinylService.findById(v.getId()).getUser().getUsername()));
                            return vinylViewModel;
                        })
                        .collect(Collectors.toList()));
    }
}
