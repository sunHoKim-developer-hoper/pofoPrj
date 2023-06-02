package kr.co.pofo.pofoapiboot3.admin;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class SelectScheduler {

  @Autowired
  VisitService visitService;

  @Scheduled(cron = "0 0 0 * * *") // 매일 정각에 실행 (초, 분, 시, 일, 월, 요일)
  public void executeTask() {
    // 여기에 스케줄 실행 시 수행할 작업을 작성합니다.
    System.out.println("매일 정각에 실행되는 작업입니다.");
  }
}
