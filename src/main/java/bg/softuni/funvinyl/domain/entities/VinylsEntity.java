package bg.softuni.funvinyl.domain.entities;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.math.BigDecimal;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vinyls")
public class VinylsEntity extends BaseEntity {

    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "artist", nullable = false)
    private String artist;
    @Column(name = "description")
    private String description;
    @Column(name = "image_url", nullable = false)
    private String imageUrl;
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    @Column(name= "create_date", nullable = false)
    protected LocalDateTime created;

    @Column(name = "contact")
    private String contact;

    @ManyToOne
    private CategoryEntity category;

/*    @ManyToMany(targetEntity = CategoryEntity.class)
    @JoinTable(name = "vinyls_category", joinColumns = @JoinColumn(name = "vinyl_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    private List<CategoryEntity> category;*/

    @ManyToOne//(fetch = FetchType.EAGER)
    private UserEntity user;

   @OneToMany(cascade = CascadeType.ALL)
    private List<CommentsEntity> comments = new ArrayList<>();

    public VinylsEntity() {
    }

    @PrePersist
    public void prePersist(){
        setCreated(LocalDateTime.now());
    }


    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public List<CommentsEntity> getComments() {
        return comments;
    }

    public void setComments(List<CommentsEntity> comments) {
        this.comments = comments;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }



    
}
