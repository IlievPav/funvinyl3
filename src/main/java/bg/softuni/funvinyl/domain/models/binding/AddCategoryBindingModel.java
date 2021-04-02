package bg.softuni.funvinyl.domain.models.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AddCategoryBindingModel {

    @NotEmpty
    private String category;

    public AddCategoryBindingModel() {
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
