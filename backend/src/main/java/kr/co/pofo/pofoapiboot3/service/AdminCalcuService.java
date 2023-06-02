package kr.co.pofo.pofoapiboot3.service;

import java.util.List;

public interface AdminCalcuService {
  List<String> todayVisitCount();
  List<String> memberDiff();
  String AdminLogin(String admin_id, String admin_pwd);
}
