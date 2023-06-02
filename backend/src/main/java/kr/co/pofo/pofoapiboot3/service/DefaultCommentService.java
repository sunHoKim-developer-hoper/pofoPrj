package kr.co.pofo.pofoapiboot3.service;

import java.util.List;

import kr.co.pofo.pofoapiboot3.entity.Comment;
import kr.co.pofo.pofoapiboot3.entity.CommentView;
import org.springframework.stereotype.Service;

import kr.co.pofo.pofoapiboot3.repository.CommentRepository;

@Service
public class DefaultCommentService implements CommentService {
    private final CommentRepository repository;

    public DefaultCommentService(CommentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Comment> getComments(Integer portfolioId) {
        List<Comment> portfolioComments = repository.findComments(portfolioId);
        return portfolioComments;
    }

    @Override
    public List<CommentView> getCommentViews(Integer portfolioId) {
        List<CommentView> portfolioCommentViews = repository.findCommentViews(portfolioId);
        return portfolioCommentViews;
    }

    @Override
    public void save(Comment comment) {
        repository.insert(comment);
    }

    @Override
    public void delete(Comment comment) {
        repository.delete(comment);
    }
}
