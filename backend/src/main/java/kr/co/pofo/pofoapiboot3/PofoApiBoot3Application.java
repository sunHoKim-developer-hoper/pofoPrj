package kr.co.pofo.pofoapiboot3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.PropertySource;

import kr.co.pofo.pofoapiboot3.util.DotenvLoader;

@SpringBootApplication
//@PropertySource(value = {"classpath:${spring.profiles.active:dev}.properties"}) 활성화된 profile 설정 가능 -> spring.profiles.active를 활용하는 방식으로 결정
public class PofoApiBoot3Application {

  public static void main(String[] args) {
    
    //설정값 세팅 로직
    DotenvLoader.loadEnv();
    SpringApplication.run(PofoApiBoot3Application.class, args);
  }
}
