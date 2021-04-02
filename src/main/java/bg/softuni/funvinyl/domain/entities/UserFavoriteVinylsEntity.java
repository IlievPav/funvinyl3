package bg.softuni.funvinyl.domain.entities;

import javax.persistence.*;


@Entity
@Table(name = "favorite")
public class UserFavoriteVinylsEntity extends BaseEntity{

    @Column(name = "user_id")
    private String userId;
    @Column(name = "vinyl_id")
    private String vinylId;

    public UserFavoriteVinylsEntity() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVinylId() {
        return vinylId;
    }

    public void setVinylId(String vinylId) {
        this.vinylId = vinylId;
    }
}
