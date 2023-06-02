package kr.co.pofo.pofoapiboot3.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SingleVisitInterceptor implements HandlerInterceptor {

  private final RedisTemplate<String, String> redisTemplate;
  private final ValueOperations<String, String> valueOperations;

  public SingleVisitInterceptor(RedisTemplate<String, String> redisTemplate) {
    this.redisTemplate = redisTemplate;
    this.valueOperations = redisTemplate.opsForValue();
  }

  public String getIp(HttpServletRequest request) {
    String ip = request.getHeader("X-Forwarded-For");

    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }

    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }

    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_CLIENT_IP");
    }

    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_X_FORWARDED_FOR");
    }

    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }

    return ip;
  }

  @Override
  public boolean preHandle(
    HttpServletRequest request,
    HttpServletResponse response,
    Object handler
  ) throws Exception {
    String userIp = getIp(request);
    String userAgent = request.getHeader("User-Agent");
    String today = LocalDate.now().toString();

    String key = today;

    Long incrementedValue = valueOperations.increment(key, 1);

    if (incrementedValue == 1) {
      // 새로운 키를 생성했을 경우에만 설정합니다.
      redisTemplate.expire(key, 24, TimeUnit.HOURS);
    }

    // 생성된 키에 대한 작업을 수행합니다.

    return true;
  }
}
