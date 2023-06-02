package kr.co.pofo.pofoapiboot3.admin;

import java.util.List;
import java.util.Map;
import kr.co.pofo.pofoapiboot3.service.AdminCalcuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/dashboard")
public class AdminController {

  private boolean loggedIn = false;

  @GetMapping("/")
  @ResponseBody
  public List<Map<String, Object>> getChartData() {
    String query =
      "SELECT s.eng_name, COUNT(*) AS count " +
      "FROM used_skill AS u " +
      "JOIN skill AS s ON u.skill_id = s.id " +
      "GROUP BY u.skill_id, s.eng_name " +
      "ORDER BY COUNT(*) DESC " +
      "LIMIT 3";

    List<Map<String, Object>> chartData = jdbcTemplate.queryForList(query);
    return chartData;
  }

  @GetMapping("/chartjs")
  public String chartjs() {
    return "myChart.html";
  }

  @Autowired
  VisitService visitService;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  AdminCalcuService adminCalcuService;

  @Autowired
  AdminSelector adminSelector;

  @GetMapping("/admin")
  public String admin(Model model) {
    if (loggedIn) {
      // 로그인 상태인 경우에만 admin.html로 이동
      List<String> resultVisit = adminSelector.todayVisitCount();
      List<String> resultDiff = adminSelector.getDiffPercentAndStatus();
      List<String> resultSignupStatus = adminSelector.getSignupStats();

      System.out.println(resultVisit.get(0));
      System.out.println(resultVisit.get(1));
      model.addAttribute("growth_rate", resultVisit.get(0));
      model.addAttribute("change_status", resultVisit.get(1));
      System.out.println(resultDiff.get(0));
      System.out.println(resultDiff.get(1));
      model.addAttribute("diff_percent", resultDiff.get(0));
      model.addAttribute("diffStatus", resultDiff.get(1));
      System.out.println(resultSignupStatus.get(0));
      System.out.println(resultSignupStatus.get(1));
      model.addAttribute("signup_diffPercent", resultSignupStatus.get(0));
      model.addAttribute("signup_diffStatus", resultSignupStatus.get(1));
      // 데이터를 모델에 추가
      return "admin.html";
    } else {
      // 로그인 상태가 아닌 경우에는 로그인 페이지로 리다이렉트
      return "redirect:/dashboard/login";
    }
  }

  @GetMapping("/login")
  public String login() {
    return "login.html";
  }

  @PostMapping("/login")
  public String login(
    @RequestParam("admin_id") String admin_id,
    @RequestParam("admin_pwd") String admin_pwd,
    Model model
  ) {
    // model.addAttribute("id", id);
    // model.addAttribute("pwd", pwd);
    String result = adminCalcuService.AdminLogin(admin_id, admin_pwd);
    if (result != null) {
      model.addAttribute("message", "로그인이 성공적으로 이루어졌습니다.");
      model.addAttribute("alertType", "success");
      loggedIn = true;
      return "redirect:admin";
    } else {
      model.addAttribute(
        "message",
        "아이디 또는 비밀번호가 올바르지 않습니다."
      );
      // 로그인 실패
      model.addAttribute(
        "errorMessage",
        "아이디 또는 비밀번호가 올바르지 않습니다."
      );
      return "redirect:login?login_error=true";
    }
  }
}
