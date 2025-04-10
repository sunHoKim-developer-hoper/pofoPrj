package kr.co.pofo.pofoapiboot3.config.filter;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.pofo.pofoapiboot3.config.appUserDetails.AppUserDetails;
import kr.co.pofo.pofoapiboot3.entity.LoginRequest;
import kr.co.pofo.pofoapiboot3.util.JwtTokenProvider;

@Component
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    

    public JwtLoginFilter(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider){
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        setFilterProcessesUrl("/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            //userDetails 객체는 보안 로직을 위한 갹체이므로 사용하지 않는 것이 적합
            LoginRequest loginRequest = mapper.readValue(request.getInputStream(), LoginRequest.class);
            //loadUserByUsername이 자동적으로 실행돼서 SecurityContext에 사용자 정보 저장 AppUserDetails user = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()); //비밀번호 비교도 Spring Security가 대신 도와준다.
            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException("로그인 요청 처리 중 예외 발생");
        }
    }
    //로그인 성공 시 !!
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authResult) throws IOException{  
        String email = ((AppUserDetails) authResult.getPrincipal()).getEmail();
        String token = jwtTokenProvider.generateToken(email);
        response.setContentType("application/json");
        response.getWriter().write("{\"token\": \"" + token + "\"}");
    }
}
