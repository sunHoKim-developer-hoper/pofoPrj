package kr.co.pofo.pofoapiboot3.service;

import java.util.List;

import kr.co.pofo.pofoapiboot3.entity.LikeDto;
import kr.co.pofo.pofoapiboot3.entity.Portfolio;
import kr.co.pofo.pofoapiboot3.entity.PortfolioLike;

public interface PortfolioLikeService {
    List<Portfolio> getByUserId(int id);

    List<PortfolioLike> getByPortfolioId(Integer portfolioId);

    Integer count(int memberId);

    Integer countByPortfolioId(Integer portfolioId);

    void insert(LikeDto likeDto);

    void delete(LikeDto likeDto);
}
