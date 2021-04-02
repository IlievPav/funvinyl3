package bg.softuni.funvinyl.repository;

import bg.softuni.funvinyl.domain.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, String> {

    CategoryEntity findCategoryEntitiesByCategoryName(String catName);

    @Query("SELECT c FROM CategoryEntity as c ORDER BY c.categoryName ASC ")
    List<CategoryEntity> findAllOrdered();



}
