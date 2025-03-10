package app.beerMeet.BeerMeet.controller;

import app.beerMeet.BeerMeet.entity.Comment;
import app.beerMeet.BeerMeet.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping
    public Comment createComment(@RequestBody Comment comment) {
        return commentService.createComment(comment);
    }

    @GetMapping("/event/{eventId}")
    public List<Comment> getCommentsByEventId(@PathVariable Long eventId) {
        return commentService.getCommentsByEventId(eventId);
    }
}
