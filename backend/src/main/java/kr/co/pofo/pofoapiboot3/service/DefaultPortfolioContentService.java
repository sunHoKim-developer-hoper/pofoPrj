package kr.co.pofo.pofoapiboot3.service;

import kr.co.pofo.pofoapiboot3.entity.PortfolioContents;
import kr.co.pofo.pofoapiboot3.repository.PortfolioContentsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DefaultPortfolioContentService implements PortfolioContentService {
    PortfolioContentsRepository portfolioContentsRepository;

    public DefaultPortfolioContentService(PortfolioContentsRepository portfolioContentsRepository) {
        this.portfolioContentsRepository = portfolioContentsRepository;
    }

    @Override
    public List<PortfolioContents> getPortfolioContents(Integer portfolioId) {
        List<PortfolioContents> list = portfolioContentsRepository.selectByPortfolioId(portfolioId);
        return list;
    }

    @Override
    public void insert(PortfolioContents portfolioContents) {
        portfolioContentsRepository.insert(portfolioContents);
    }

    @Override
    public void update(PortfolioContents portfolioContents) {
        portfolioContentsRepository.update(portfolioContents);
    }

    @Override
    public void delete(PortfolioContents portfolioContents) {
        portfolioContentsRepository.delete(portfolioContents);
    }
}
