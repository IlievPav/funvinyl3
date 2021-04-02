package bg.softuni.funvinyl.domain.models.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class AddCommentsBindingModel {
    @NotBlank
    private String comment;

    public AddCommentsBindingModel() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
