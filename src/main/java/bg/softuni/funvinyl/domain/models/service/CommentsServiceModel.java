package bg.softuni.funvinyl.domain.models.service;

public class CommentsServiceModel extends BaseServiceModel{

    private String comment;

    public CommentsServiceModel() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
