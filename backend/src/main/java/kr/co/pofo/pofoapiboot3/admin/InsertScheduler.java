package kr.co.pofo.pofoapiboot3.admin;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class InsertScheduler {

  @Autowired
  VisitService visitService;

  @Scheduled(cron = "0 55 23 * * *") // 매일 오후 11시 55분에 실행 (초, 분, 시, 일, 월, 요일)
  public void executeTask() {
    LocalDateTime currentTime = LocalDateTime.now();
    System.out.println("스케줄 실행 시간: " + currentTime);
    visitService.insertVisitsToMariaDB();
    System.out.println("insert!!!!!!!!!!!");
    // 원하는 작업을 수행합니다.
  }
}
