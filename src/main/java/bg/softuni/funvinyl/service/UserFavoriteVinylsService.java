package bg.softuni.funvinyl.service;

import bg.softuni.funvinyl.domain.entities.UserFavoriteVinylsEntity;

import java.util.List;
import java.util.Optional;

public interface UserFavoriteVinylsService {

      void addToFavorites(String id, String name);

    List<String> getAllFavorites(String id);
}
