package kr.co.pofo.pofoapiboot3.repository;

import kr.co.pofo.pofoapiboot3.entity.Report;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReportRepository {
    List<Report> selectByPortfolioId(Integer memberId);
    void insert(Report report);

    void deletePortfolioReport(Report report);
}
