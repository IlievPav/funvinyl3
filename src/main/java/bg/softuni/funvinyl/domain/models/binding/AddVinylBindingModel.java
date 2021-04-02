package bg.softuni.funvinyl.domain.models.binding;


import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class AddVinylBindingModel {

    @NotBlank
    @Size(min = 2, message = "to short")
    private String artist;
    @NotBlank
    @Size(min = 2)
    private String title;
    @DecimalMin("0")
    private BigDecimal price;

    private AddCategoryBindingModel categories;
    private List<AddCommentsBindingModel> comments = new ArrayList<>();

    private MultipartFile imageUrl;
    private String description;
    private String contact;
    private String username;

    public AddVinylBindingModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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

    public AddCategoryBindingModel getCategories() {
        return categories;
    }

    public void setCategories(AddCategoryBindingModel categories) {
        this.categories = categories;
    }

    public List<AddCommentsBindingModel> getComments() {
        return comments;
    }

    public void setComments(List<AddCommentsBindingModel> comments) {
        this.comments = comments;
    }

    public MultipartFile getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(MultipartFile imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
