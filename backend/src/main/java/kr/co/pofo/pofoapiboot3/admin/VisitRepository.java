package kr.co.pofo.pofoapiboot3.admin;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface VisitRepository {
  @Insert(
    "INSERT INTO visits (visit_date, visit_count) VALUES (#{date}, #{count})"
  )
  void insertVisit(@Param("date") String date, @Param("count") int count);
}
