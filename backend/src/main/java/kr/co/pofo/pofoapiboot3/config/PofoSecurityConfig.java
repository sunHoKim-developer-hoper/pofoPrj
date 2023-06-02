package kr.co.pofo.pofoapiboot3.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class PofoSecurityConfig implements WebMvcConfigurer {

  //docs.spring.io/spring-security/reference/servlet/authorization/expression-based.html
  //www.baeldung.com/spring-security-thymeleaf

  private final SingleVisitInterceptor singleVisitInterceptor;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    
    http.cors().and()
            .csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers("/admin/**").hasAnyRole("ADMIN")
            .requestMatchers("/member/**").hasAnyRole("ADMIN", "MEMBER")
            .anyRequest().permitAll();

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry
      .addInterceptor(singleVisitInterceptor)
      .addPathPatterns("/user/login/**");
  }
}
