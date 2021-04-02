package bg.softuni.funvinyl.domain.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity{

    @Column(name = "username",unique = true, nullable = false)
    private String username;

    @Column(name = "fullname", nullable = false)
    private String fullName;

    @Column(name = "email",unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;



    @ManyToMany(fetch = FetchType.EAGER)
    private List<UserRoleEntity> roles = new ArrayList<>();

    /*@OneToMany(cascade = CascadeType.ALL)
    private List<VinylsEntity> favorite;*/

    public UserEntity() {
    }
/*
    public List<VinylsEntity> getFavorite() {
        return favorite;
    }

    public void setFavorite(List<VinylsEntity> favorite) {
        this.favorite = favorite;
    }*/

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserRoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRoleEntity> roles) {
        this.roles = roles;
    }


    public UserEntity addRole(UserRoleEntity roleEntity){
        this.roles.add(roleEntity);
        return this;
    }
}
