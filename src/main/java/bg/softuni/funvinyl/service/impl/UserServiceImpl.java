package bg.softuni.funvinyl.service.impl;

import bg.softuni.funvinyl.domain.entities.UserEntity;
import bg.softuni.funvinyl.domain.entities.UserRoleEntity;
import bg.softuni.funvinyl.domain.entities.enums.UserRoleEnum;
import bg.softuni.funvinyl.domain.models.service.UserRegisterServiceModel;
import bg.softuni.funvinyl.repository.UserRepository;
import bg.softuni.funvinyl.repository.UserRoleRepository;
import bg.softuni.funvinyl.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final VinylUserService vinylUserService;



    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository,
                           PasswordEncoder passwordEncoder, ModelMapper modelMapper,
                           VinylUserService vinylUserService) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.vinylUserService = vinylUserService;
    }

    @Override
    public Optional<UserEntity> findByUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void seedUsers() {

        if (userRepository.count() == 0) {
            UserRoleEntity adminRole = new UserRoleEntity().setRole(UserRoleEnum.ADMIN);
            UserRoleEntity userRole = new UserRoleEntity().setRole(UserRoleEnum.USER);

            userRoleRepository.saveAll(List.of(adminRole, userRole));

            UserEntity admin =new UserEntity();
            admin.setUsername("admin");
            admin.setFullName("adminov");
            admin.setEmail("adminov@gmail.com");
            admin.setPassword(passwordEncoder.encode("11111"));
            admin.setRoles(List.of(adminRole, userRole));

            UserEntity user =new UserEntity();
            user.setUsername("user");
            user.setFullName("userov");
            user.setEmail("userov@gmail.com");
            user.setPassword(passwordEncoder.encode("11111"));
            user.setRoles(List.of( userRole));

            userRepository.saveAll(List.of(admin, user));
        }


    }

    @Override
    public boolean userNameExists(String username) {
                   return userRepository.findByUsername(username).isPresent();
           }
    @Override
    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }


    @Override
    public UserEntity registerAndLoginUser(UserRegisterServiceModel userRegisterServiceModel) {
        UserEntity newUser = modelMapper.map(userRegisterServiceModel, UserEntity.class);
        newUser.setPassword(passwordEncoder.encode(userRegisterServiceModel.getPassword()));

        UserRoleEntity userRole = userRoleRepository.findByRole(UserRoleEnum.USER).orElseThrow(
                () -> new IllegalStateException("USER role not found. please seed roles!"));

        newUser.addRole(userRole);

        newUser = userRepository.save(newUser);

        return newUser;
    }

    @Override
    public void loginUser(UserEntity newUser) {

        UserDetails principal = vinylUserService.loadUserByUsername(newUser.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal,
                newUser.getPassword(),
                principal.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


}
