package bg.softuni.funvinyl.repository;

import bg.softuni.funvinyl.domain.entities.UserRoleEntity;
import bg.softuni.funvinyl.domain.entities.VinylsEntity;
import bg.softuni.funvinyl.domain.entities.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, String > {

    Optional<UserRoleEntity> findByRole(UserRoleEnum role);

}
