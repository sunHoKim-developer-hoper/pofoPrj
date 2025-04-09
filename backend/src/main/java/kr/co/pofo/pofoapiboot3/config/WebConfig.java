package kr.co.pofo.pofoapiboot3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
@Configuration
public class WebConfig implements WebMvcConfigurer {
  
  // 아래의 설정은 응답 헤더에 cors를 붙인다는 의미이고 브라우저가 다른 출처에서 온 응답을 신뢰할 수 있도록 cors 관련 HTTP 헤더를 붙인다는 뜻이다.
  // 붙여야 하는 이유는 브라우저는 보안 상의 이유로 다른 서버에서 응답이 오면, 그 응답을 기본적으로 차단하기 때문이다.
  @Override
  public void addCorsMappings(CorsRegistry registry) {
      registry.addMapping("/**").allowedOriginPatterns("*").allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(), HttpMethod.DELETE.name()).allowCredentials(true); //.allowedOrigins(:"*")와 allowCredentials(true)랑 같이 사용이 불가하다하여 Patterns로 수정
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
