package bg.softuni.funvinyl.vinyl.service.impl;

import bg.softuni.funvinyl.domain.entities.UserEntity;
import bg.softuni.funvinyl.domain.entities.UserRoleEntity;
import bg.softuni.funvinyl.domain.entities.enums.UserRoleEnum;
import bg.softuni.funvinyl.domain.models.service.UserRegisterServiceModel;
import bg.softuni.funvinyl.repository.UserRepository;
import bg.softuni.funvinyl.repository.UserRoleRepository;
import bg.softuni.funvinyl.service.UserRoleService;

import bg.softuni.funvinyl.service.VinylService;
import bg.softuni.funvinyl.service.impl.UserServiceImpl;
import bg.softuni.funvinyl.service.impl.VinylUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Transactional
public class UserServiceTest {

    private UserServiceImpl userService;
    @Mock
    UserRepository userRepository;
    @Mock
    UserRoleRepository userRoleRepository;
    @Mock
    ModelMapper modelMapper;
    @Mock
    VinylUserService vinylUserService;
    @Mock
    PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp(){
        userService = new UserServiceImpl(userRepository, userRoleRepository,
                passwordEncoder, modelMapper, vinylUserService);
    }
    @Test
    void registerTest(){
        UserRegisterServiceModel userRegisterServiceModel = new UserRegisterServiceModel();
        userRegisterServiceModel.setEmail("check@");
        userRegisterServiceModel.setUsername("gosho");
        userRegisterServiceModel.setFullName("Gosho goshev");
        userRegisterServiceModel.setPassword(("11111"));

        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setRole(UserRoleEnum.USER);
        Mockito.when(userRoleRepository.findByRole(UserRoleEnum.USER)).thenReturn(java.util.Optional.of(userRoleEntity));

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("check@");
        userEntity.setUsername("gosho");
        userEntity.setPassword(("11111"));
        userEntity.setFullName("Gosho goshev");
        Mockito.when(modelMapper.map(userRegisterServiceModel, UserEntity.class)).thenReturn(userEntity);
        List<UserRoleEntity> userRoleEntityList = new ArrayList<>();
        userRoleEntityList.add(userRoleEntity);
        userEntity.setRoles(userRoleEntityList);

        List<GrantedAuthority> authorities =
                userEntity.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_"+ role.getRole().name()))
                        .collect(Collectors.toList());
        UserDetails result = new User(
                userEntity.getUsername(),
                userEntity.getPassword(),authorities
        );

        userService.registerAndLoginUser(userRegisterServiceModel);
        Mockito.verify(userRepository).save(any());
    }

}
