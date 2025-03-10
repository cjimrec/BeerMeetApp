package app.beerMeet.BeerMeet.service;

import app.beerMeet.BeerMeet.entity.Comment;
import app.beerMeet.BeerMeet.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByEventId(Long eventId) {
        return commentRepository.findByEventId(eventId);
    }
}