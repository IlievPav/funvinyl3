package bg.softuni.funvinyl.service.impl;

import bg.softuni.funvinyl.domain.entities.UserEntity;
import bg.softuni.funvinyl.domain.entities.UserFavoriteVinylsEntity;
import bg.softuni.funvinyl.domain.entities.VinylsEntity;
import bg.softuni.funvinyl.repository.UserFavoriteVinylRepository;
import bg.softuni.funvinyl.service.UserFavoriteVinylsService;
import bg.softuni.funvinyl.service.UserService;
import bg.softuni.funvinyl.service.VinylService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserFavoriteVinylsServiceImpl implements UserFavoriteVinylsService {


    private final UserFavoriteVinylRepository userFavoriteVinylRepository;
    private final VinylService vinylService;
    private final UserService userService;

    public UserFavoriteVinylsServiceImpl(UserFavoriteVinylRepository userFavoriteVinylRepository, VinylService vinylService, UserService userService) {
        this.userFavoriteVinylRepository = userFavoriteVinylRepository;
        this.vinylService = vinylService;
        this.userService = userService;
    }

    @Override
    public void addToFavorites(String id, String name) {
        VinylsEntity vinylsEntity = this.vinylService.findById(id);
        Optional<UserEntity> userEntity = userService.findByUser(name);
        List<String> allVinylIdByUserId = this.userFavoriteVinylRepository.findAllVinylIdByUserId(userEntity.get().getId());
        boolean contains = allVinylIdByUserId.contains(vinylsEntity.getId());

        if (!contains){
        Optional<UserEntity> user = this.userService.findByUser(name);
        UserFavoriteVinylsEntity userFavoriteVinylsEntity = new UserFavoriteVinylsEntity();
        userFavoriteVinylsEntity.setUserId(user.get().getId());
        userFavoriteVinylsEntity.setVinylId(vinylsEntity.getId());
        this.userFavoriteVinylRepository.saveAndFlush(userFavoriteVinylsEntity);
        }
    }

    @Override
    public  List<String> getAllFavorites(String id) {
        List<String> allByUserId = this.userFavoriteVinylRepository.findAllVinylIdByUserId(id);
        System.out.println();

        return allByUserId;
    }


}
