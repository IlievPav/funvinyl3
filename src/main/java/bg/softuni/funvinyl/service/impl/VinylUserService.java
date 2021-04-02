package bg.softuni.funvinyl.service.impl;

import bg.softuni.funvinyl.domain.entities.UserEntity;
import bg.softuni.funvinyl.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VinylUserService implements UserDetailsService {

    private final UserRepository userRepository;

    public VinylUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with name " + username + " was not found!"));

        return mapToUserDetails(userEntity);
    }

    private UserDetails mapToUserDetails(UserEntity userEntity) {
        List<GrantedAuthority> authorities =
                userEntity.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_"+ role.getRole().name()))
                        .collect(Collectors.toList());

        UserDetails result = new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                authorities);

        return result;
    }


}
