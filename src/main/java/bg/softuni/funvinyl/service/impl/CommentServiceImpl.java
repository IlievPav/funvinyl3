package bg.softuni.funvinyl.service.impl;

import bg.softuni.funvinyl.domain.entities.CommentsEntity;
import bg.softuni.funvinyl.domain.entities.UserEntity;
import bg.softuni.funvinyl.domain.models.service.CategoryServiceModel;
import bg.softuni.funvinyl.domain.models.service.CommentsServiceModel;
import bg.softuni.funvinyl.repository.CommentsRepository;
import bg.softuni.funvinyl.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentsRepository commentsRepository;
    private final ModelMapper modelMapper;

    public CommentServiceImpl(CommentsRepository commentsRepository, ModelMapper modelMapper) {
        this.commentsRepository = commentsRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<CommentsServiceModel> findAllComments() {
        return this.commentsRepository.findAll()
                .stream()
                .map(c -> modelMapper.map(c, CommentsServiceModel.class))
                .collect(Collectors.toList());

    }

    @Override
    public CommentsEntity createEmptyCommentsEntities() {
        CommentsEntity commentsEntity = new CommentsEntity();
        commentsRepository.save(commentsEntity);
      return commentsEntity;
    }

    @Override
    public List<CommentsEntity> findCommentsByVinylId(String id) {
        List<CommentsEntity> commentsEntity = this.commentsRepository.findCommentsEntityByVinylId(id);

        return commentsEntity;
    }


    /*RABOTI List<CommentsEntity> commentsByVinylId = commentService.findCommentsByVinylId(id);
        System.out.printf("");*/
}
