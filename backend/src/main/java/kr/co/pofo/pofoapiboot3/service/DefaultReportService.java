package kr.co.pofo.pofoapiboot3.service;

import kr.co.pofo.pofoapiboot3.entity.Report;
import kr.co.pofo.pofoapiboot3.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultReportService implements ReportService{
    private final ReportRepository repository;

    public DefaultReportService(ReportRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Report> getByPortfolioId(Integer portfolioId) {
        return repository.selectByPortfolioId(portfolioId);
    }

    @Override
    public void post(Report report) {
        repository.insert(report);
    }

    @Override
    public void deletePortfolioReport(Report report) {
        repository.deletePortfolioReport(report);
    }
}
