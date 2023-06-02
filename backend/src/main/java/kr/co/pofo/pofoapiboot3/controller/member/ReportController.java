package kr.co.pofo.pofoapiboot3.controller.member;

import kr.co.pofo.pofoapiboot3.entity.Report;
import kr.co.pofo.pofoapiboot3.service.ReportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/pofo/{portfolioId}/reports")
public class ReportController {
    private final ReportService service;

    public ReportController(ReportService service) {
        this.service = service;
    }

    @GetMapping
    public List<Report> get(@PathVariable Integer portfolioId) {
        return service.getByPortfolioId(portfolioId);
    }

    @PostMapping
    public void post(@RequestBody Report report) {
        service.post(report);
    }

    @DeleteMapping
    public void deletePortfolioReport(@RequestBody Report report) {
        service.deletePortfolioReport(report);
    }
}
