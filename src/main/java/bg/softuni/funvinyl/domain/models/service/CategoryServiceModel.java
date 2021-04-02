package bg.softuni.funvinyl.domain.models.service;

public class CategoryServiceModel extends BaseServiceModel{

    private String category;

    public CategoryServiceModel() {
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
