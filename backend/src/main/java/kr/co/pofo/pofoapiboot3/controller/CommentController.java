package kr.co.pofo.pofoapiboot3.controller;

import kr.co.pofo.pofoapiboot3.entity.Comment;
import kr.co.pofo.pofoapiboot3.entity.CommentView;
import kr.co.pofo.pofoapiboot3.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/pofo/{portfolioId}/comments")
public class CommentController {
    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @GetMapping
    public List<CommentView> getCommentViews(@PathVariable("portfolioId") Integer portfolioId) {
        List<CommentView> commentViews = service.getCommentViews(portfolioId);
        return commentViews;
    }

    @PostMapping
    public void save(@RequestBody Comment comment) {
        service.save(comment);
    }

    @DeleteMapping
    public void delete(@RequestBody Comment comment) {
        service.delete(comment);
    }
}
