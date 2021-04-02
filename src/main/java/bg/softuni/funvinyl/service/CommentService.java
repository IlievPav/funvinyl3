package bg.softuni.funvinyl.service;

import bg.softuni.funvinyl.domain.entities.CommentsEntity;
import bg.softuni.funvinyl.domain.entities.UserEntity;
import bg.softuni.funvinyl.domain.models.service.CategoryServiceModel;
import bg.softuni.funvinyl.domain.models.service.CommentsServiceModel;

import java.util.List;

public interface CommentService {
    List<CommentsServiceModel> findAllComments();

    CommentsEntity createEmptyCommentsEntities();

    List<CommentsEntity> findCommentsByVinylId(String id);
}
