package bg.softuni.funvinyl.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class CommentsEntity extends BaseEntity{

    @Column(name = "comment", updatable = true)
    private String comments;

   /* @ManyToOne
    private UserEntity user;*/

    public CommentsEntity() {
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    /*public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }*/
}
