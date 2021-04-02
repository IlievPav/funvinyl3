package bg.softuni.funvinyl.repository;

import bg.softuni.funvinyl.domain.entities.UserFavoriteVinylsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFavoriteVinylRepository extends JpaRepository<UserFavoriteVinylsEntity, String> {

    @Query("SELECT f.vinylId FROM UserFavoriteVinylsEntity as f WHERE f.userId = :userId")
    List<String> findAllVinylIdByUserId(@Param("userId") String id);

    /*List<String> findAllByUserId(String id);*/
}
