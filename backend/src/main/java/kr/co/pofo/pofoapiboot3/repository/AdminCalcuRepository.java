package kr.co.pofo.pofoapiboot3.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminCalcuRepository {
  List<String> todayVisitCount();
  List<String> memberDiff();
  String AdminLogin(String admin_id, String admin_pwd);
}
