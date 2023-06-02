package kr.co.pofo.pofoapiboot3.repository;

import kr.co.pofo.pofoapiboot3.entity.PortfolioContents;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PortfolioContentsRepository {
    List<PortfolioContents> selectByPortfolioId(Integer portfolioId);

    void insert(PortfolioContents portfolioContents);

    void update(PortfolioContents portfolioContents);

    void delete(PortfolioContents portfolioContents);
}
