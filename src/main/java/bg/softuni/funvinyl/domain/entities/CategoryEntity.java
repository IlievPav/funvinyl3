package bg.softuni.funvinyl.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "category")
public class CategoryEntity extends BaseEntity {

    @Column(name = "category_name", updatable = true)
    private String categoryName;

    public CategoryEntity() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "CategoryEntity{" +
                "categoryName='" + categoryName + '\'' +
                '}';
    }
}
