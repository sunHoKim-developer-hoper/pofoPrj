package kr.co.pofo.pofoapiboot3.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class AdminSelector {

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public AdminSelector(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<String> todayVisitCount() {
    List<String> resultVisit = new ArrayList<>();

    String sql1 =
      "SELECT growth_rate, ' ', change_status FROM visit_view LIMIT 1";

    List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql1);
    for (Map<String, Object> row : rows) {
      String growthRate = row.get("growth_rate").toString();
      String changeStatus = row.get("change_status").toString();
      resultVisit.add(growthRate);
      resultVisit.add(changeStatus);
    }

    return resultVisit;
  }

  public List<String> getDiffPercentAndStatus() {
    List<String> resultDiff = new ArrayList<>();

    String sql = "SELECT diff_percent, status FROM member_diff_view";

    List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
    for (Map<String, Object> row : rows) {
      String diffPercent = row.get("diff_percent").toString();
      String status = row.get("status").toString();
      String diffStatus = status;
      resultDiff.add(diffPercent);
      resultDiff.add(diffStatus);
    }

    return resultDiff;
  }

  public List<String> getSignupStats() {
    List<String> signupStats = new ArrayList<>();

    String sql =
      "SELECT signup_diff_percent, signup_status FROM signup_stats_view";

    List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
    for (Map<String, Object> row : rows) {
      String diffPercent = row.get("signup_diff_percent").toString();
      String status = row.get("signup_status").toString();
      signupStats.add(diffPercent);
      signupStats.add(status);
    }

    return signupStats;
  }
}
