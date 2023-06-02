package kr.co.pofo.pofoapiboot3.admin;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChartController {

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public ChartController(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @GetMapping("/chart-data")
  public List<Map<String, Object>> getChartData() {
    String sql =
      "SELECT s.eng_name, COUNT(*) AS count " +
      "FROM used_skill AS u " +
      "JOIN skill AS s ON u.skill_id = s.id " +
      "GROUP BY u.skill_id, s.eng_name " +
      "ORDER BY COUNT(*) DESC " +
      "LIMIT 3";

    return jdbcTemplate.queryForList(sql);
  }
}
