package kr.co.pofo.pofoapiboot3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.pofo.pofoapiboot3.entity.Portfolio;
import kr.co.pofo.pofoapiboot3.entity.PortfolioContents;
import kr.co.pofo.pofoapiboot3.entity.PortfolioView;
import kr.co.pofo.pofoapiboot3.entity.WeeklyPopularPortfolioView;
import kr.co.pofo.pofoapiboot3.repository.PortfolioRepository;
import kr.co.pofo.pofoapiboot3.repository.SkillRepository;

@Service
public class DefaultPortfolioService implements PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;
    @Autowired
    private SkillRepository skillRepository;

    // 디폴트 포트폴리오 리스트
    @Override
    public List<PortfolioView> getViewList(Integer page, String sort, Integer collaboration, Integer skillId) {
    	
        int size = 15; // 포트폴리오를 한 번에 15개씩 가져옴
        return portfolioRepository.findViewAll(page, size, sort, collaboration, skillId);
    }

    // 검색된 포트폴리오 리스트
    @Override
    public List<PortfolioView> getViewListByQuery(Integer page, String sort, Integer collaboration, String query) {
        int size = 15; // 포트폴리오를 한 번에 15개씩 가져옴

        // query와 관련된 스택이 있는지 확인함
        int[] skillIds = portfolioRepository.findSkillIdsByQuery(query);

        return portfolioRepository.findViewAllByQuery(page, size, sort, collaboration, query, skillIds);
    }
    
    // 이번주 인기 포트폴리오 리스트
    @Override
    public List<WeeklyPopularPortfolioView> getWeeklyPopularViewList() {

        return portfolioRepository.findWeeklyPopularViewAll();
    }

    //  Index
    @Override
    public List<PortfolioView> getViewList(String query, String order) {
        List<PortfolioView> list = portfolioRepository.findViewListByQuery(query, order);

        //좋아요로 정렬 시
        return list;
    }

    @Override
    public List<PortfolioView> getViewList(Integer type, String order) {
        List<PortfolioView> list = portfolioRepository.findViewListByType(type, order);

        //좋아요로 정렬 시
        return list;
    }

    // @Override
    // public void add(Portfolio pf) {
    //     portfolioRepository.insert(pf);
    // }

    @Override
    public int getId() {
        return portfolioRepository.findId();
    }

//  Detail
    @Override
    public Portfolio getPortfolioById(Integer id) {
        return portfolioRepository.findPortfolioById(id);
    }

    @Override
    public List<Portfolio> getMorePortfolio(Integer portfolioId) {
        Portfolio currentPortfolio = portfolioRepository.findPortfolioById(portfolioId);
        Integer currentPortfolioId = currentPortfolio.getId();
        Integer memberId = currentPortfolio.getMemberId();

        List<Portfolio> list = portfolioRepository.findMorePortfolio(currentPortfolioId, memberId);

        return list;
    }

    @Override
    public List<Portfolio> getByUserId(int id) {
        return portfolioRepository.findByUserId(id);
    }

    // 조회수 증가
    @Override
    public void updateHitCount(Integer id) {
        portfolioRepository.updateHitCount(id);
    }

  
 
    //등록(이미지, 텍스트)
    public boolean regContent(Portfolio pofo, PortfolioContents pofoContent){
        Integer pofoId = pofo.getId();
        pofoContent.setPortfolioId(pofoId);
        int result = portfolioRepository.contentInsert(pofoContent);
        if(result == 1)
            return true;
        else
            return false;
    }

    @Override
    public boolean regPofo(Portfolio portfolio) {
       int result = portfolioRepository.regPofo(portfolio);
       if(result == 1){
            return true;
       }
       else
        return false;
    }

    @Override
    public int getByTitleAndMemberId(String title, Integer memberId){
        return portfolioRepository.findByTitleAndMemberId(title, memberId);
    }

    @Override
    public boolean regContent(PortfolioContents pofoContent) {
       int result = portfolioRepository.regContent(pofoContent);
       if(result == 1)
        return true;
       else
        return false;
    }
       


}
