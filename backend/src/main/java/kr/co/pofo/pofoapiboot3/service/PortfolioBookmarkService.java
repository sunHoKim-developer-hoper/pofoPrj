package kr.co.pofo.pofoapiboot3.service;

import kr.co.pofo.pofoapiboot3.entity.PortfolioBookmark;

import java.util.List;

public interface PortfolioBookmarkService {
    List<PortfolioBookmark> getListByPortfolioId(Integer portfolioId);
    void post(PortfolioBookmark bookmark);
    void delete(PortfolioBookmark bookmark);
}
