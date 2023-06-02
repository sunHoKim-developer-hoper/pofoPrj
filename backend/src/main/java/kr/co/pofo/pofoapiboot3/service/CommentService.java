package kr.co.pofo.pofoapiboot3.service;

import java.util.List;

import kr.co.pofo.pofoapiboot3.entity.Comment;
import kr.co.pofo.pofoapiboot3.entity.CommentView;

public interface CommentService {
    List<Comment> getComments(Integer portfolioId);

    List<CommentView> getCommentViews(Integer portfolioId);

    void save(Comment comment);

    void delete(Comment comment);
}
