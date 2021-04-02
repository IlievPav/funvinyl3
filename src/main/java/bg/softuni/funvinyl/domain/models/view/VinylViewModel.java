package bg.softuni.funvinyl.domain.models.view;

import bg.softuni.funvinyl.domain.entities.CategoryEntity;
import bg.softuni.funvinyl.domain.models.binding.AddCategoryBindingModel;
import bg.softuni.funvinyl.domain.models.binding.AddCommentsBindingModel;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public class VinylViewModel {

    private String id;
    private String artist;
    private String title;
    private BigDecimal price;
    private String categories;
    private String imageUrl;
    private String username;
    private boolean owner;

    public VinylViewModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
