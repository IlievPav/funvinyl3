package bg.softuni.funvinyl.repository;

import bg.softuni.funvinyl.domain.entities.CategoryEntity;
import bg.softuni.funvinyl.domain.entities.CommentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.hibernate.loader.Loader.SELECT;

@Repository
public interface CommentsRepository extends JpaRepository<CommentsEntity, String> {

    List<CommentsEntity> findAll();

    @Query("SELECT v.comments FROM VinylsEntity as v WHERE v.id = :vinylId")
    List<CommentsEntity> findCommentsEntityByVinylId(@Param("vinylId") String id);


  /*  @Query("SELECT f.vinylId FROM UserFavoriteVinylsEntity as f WHERE f.userId = :userId")
    List<String> findAllVinylIdByUserId(@Param("userId") String id);*/
}
