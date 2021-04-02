package bg.softuni.funvinyl.service;

import bg.softuni.funvinyl.domain.entities.UserEntity;
import bg.softuni.funvinyl.domain.models.service.UserRegisterServiceModel;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UserEntity> findByUser(String username);

    void seedUsers();

    boolean userNameExists(String username);
    boolean emailExists(String email);

    UserEntity registerAndLoginUser(UserRegisterServiceModel userRegisterServiceModel);
    void loginUser(UserEntity newUser);



}
