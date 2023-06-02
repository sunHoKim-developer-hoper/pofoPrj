package kr.co.pofo.pofoapiboot3.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisRepository {

  private final RedisTemplate<String, String> redisTemplate;

  public RedisRepository(RedisTemplate<String, String> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  public List<String> getAllKeys() {
    Set<String> keys = redisTemplate.keys("*");
    return new ArrayList<>(keys);
  }

  public String getValueByKey(String key) {
    return redisTemplate.opsForValue().get(key);
  }
}
