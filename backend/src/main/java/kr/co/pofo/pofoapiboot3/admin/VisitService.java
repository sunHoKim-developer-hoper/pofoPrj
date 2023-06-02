package kr.co.pofo.pofoapiboot3.admin;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class VisitService {

  private final RedisRepository redisRepository;
  private final VisitRepository visitRepository;

  public VisitService(
    RedisRepository redisRepository,
    VisitRepository visitRepository
  ) {
    this.redisRepository = redisRepository;
    this.visitRepository = visitRepository;
  }

  public void insertVisitsToMariaDB() {
    List<String> keys = redisRepository.getAllKeys();
    String today = LocalDate.now().toString();

    for (String key : keys) {
      String value = redisRepository.getValueByKey(key);
      // visit_date는 키 값으로 설정
      String visitDate = key;
      int visitCount = Integer.parseInt(value);
      if (key.equals(today)) {
        visitRepository.insertVisit(visitDate, visitCount);
      }
    }
  }
}
