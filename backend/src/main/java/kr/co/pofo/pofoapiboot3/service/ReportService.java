package kr.co.pofo.pofoapiboot3.service;

import kr.co.pofo.pofoapiboot3.entity.Report;

import java.util.List;

public interface ReportService {
    List<Report> getByPortfolioId(Integer memberId);
    void post(Report reportDto);
    void deletePortfolioReport(Report reportDto);
}
