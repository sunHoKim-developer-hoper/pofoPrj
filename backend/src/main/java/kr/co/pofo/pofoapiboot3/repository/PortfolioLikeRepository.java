package kr.co.pofo.pofoapiboot3.repository;

import kr.co.pofo.pofoapiboot3.entity.LikeDto;
import kr.co.pofo.pofoapiboot3.entity.Portfolio;
import kr.co.pofo.pofoapiboot3.entity.PortfolioLike;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PortfolioLikeRepository {
    List<Portfolio> findByUserId(int id);

    Integer count(int memberId);

    Integer countByPortfolioId(Integer portfolioId);

    void insert(LikeDto likeDto);

    void delete(LikeDto likeDto);

    List<PortfolioLike> findByPortfolioId(Integer portfolioId);
}
