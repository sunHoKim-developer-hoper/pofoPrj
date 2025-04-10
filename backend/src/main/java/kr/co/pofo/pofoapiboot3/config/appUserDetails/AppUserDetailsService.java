package kr.co.pofo.pofoapiboot3.config.appUserDetails;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.co.pofo.pofoapiboot3.entity.Member;
import kr.co.pofo.pofoapiboot3.repository.MemberRepository;

@Service
public class AppUserDetailsService implements UserDetailsService{
	@Autowired
	private MemberRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//내부적으로 authenticate()가 호출한다.
		Member member = repository.findByEmail(username);
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
		
		// 데이터가 준비되었으면 이제 UserDetails 그릇 객체를 만들어서 데이터 담아서 반환해주면 끝!!
		AppUserDetails user = new AppUserDetails();
		user.setId(member.getId());
		user.setUsername(member.getEmail());
		user.setPassword(member.getPwd());
		user.setAuthorities(authorities);
		return user;
	}
}
