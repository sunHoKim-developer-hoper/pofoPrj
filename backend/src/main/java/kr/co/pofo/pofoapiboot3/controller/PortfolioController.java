package kr.co.pofo.pofoapiboot3.controller;

import kr.co.pofo.pofoapiboot3.entity.Member;
import kr.co.pofo.pofoapiboot3.entity.Portfolio;
import kr.co.pofo.pofoapiboot3.service.MemberService;
import kr.co.pofo.pofoapiboot3.service.PortfolioService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/pofo")
public class PortfolioController {
    private final PortfolioService portfolioService;
    private final MemberService memberService;

    public PortfolioController(PortfolioService portfolioService, MemberService memberService) {
        this.portfolioService = portfolioService;
        this.memberService = memberService;
    }

    @GetMapping("{portfolioId}")
    public Map<String,Object> getPortfolio(@PathVariable("portfolioId") Integer portfolioId) {
        Portfolio portfolio = portfolioService.getPortfolioById(portfolioId);
        Member member = memberService.getById(portfolio.getMemberId());

        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("portfolio", portfolio);
        objectMap.put("member", member);

        return objectMap;
    }

    @GetMapping("{portfolioId}/more")
    public List<Portfolio> getMorePortfolio(@PathVariable("portfolioId") Integer portfolioId) {
        return portfolioService.getMorePortfolio(portfolioId);
    }

    // 조회수 증가
    @GetMapping("/update/{id}")
    public void updateHitCount(@PathVariable("id") Integer id){
        System.out.println("who am i?" + id);
        portfolioService.updateHitCount(id);
    }


}
