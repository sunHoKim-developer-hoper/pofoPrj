package kr.co.pofo.pofoapiboot3.controller;

import kr.co.pofo.pofoapiboot3.entity.PortfolioContents;
import kr.co.pofo.pofoapiboot3.service.PortfolioContentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/pofo/{portfolioId}/contents")
public class PortfolioContentController {
    private final PortfolioContentService service;

    public PortfolioContentController(PortfolioContentService service) {
        this.service = service;
    }

    @GetMapping
    public List<PortfolioContents> getPortfolioContents(@PathVariable("portfolioId") Integer portfolioId) {
        List<PortfolioContents> contents = service.getPortfolioContents(portfolioId);
        return contents;
    }
}
