package bg.softuni.funvinyl.domain.models.service;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VinylServiceModel extends BaseServiceModel{

    private String artist;
    private String title;
    private BigDecimal price;
    private CategoryServiceModel  categories;
    private List<CommentsServiceModel> comments = new ArrayList<>();
    private String imageUrl;
    private String description;
    private String contact;

      public VinylServiceModel() {
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

    public CategoryServiceModel getCategories() {
        return categories;
    }

    public void setCategories(CategoryServiceModel categories) {
        this.categories = categories;
    }

    public List<CommentsServiceModel> getComments() {
        return comments;
    }

    public void setComments(List<CommentsServiceModel> comments) {
        this.comments = comments;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
