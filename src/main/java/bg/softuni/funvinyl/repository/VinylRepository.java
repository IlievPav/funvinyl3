package bg.softuni.funvinyl.repository;

import bg.softuni.funvinyl.domain.entities.UserEntity;
import bg.softuni.funvinyl.domain.entities.VinylsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VinylRepository extends JpaRepository<VinylsEntity, String>{


  List<VinylsEntity> findTop3ByOrderByCreatedDesc();

    List<VinylsEntity> findAllByOrderByCreatedDesc();

    Optional<VinylsEntity> findById(String id);

  List<VinylsEntity> findVinylsEntityByUserId(String userId);

  /*@Query("SELECT v.user.username FROM VinylsEntity v WHERE v.id='id'")
  String findUserByVinylID(String id);*/



}
