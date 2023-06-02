package kr.co.pofo.pofoapiboot3.service;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.pofo.pofoapiboot3.entity.Community;

@SpringBootTest
@AutoConfigureMybatis
class DefaultCommunityServiceTest {
	
	@Autowired
	private CommunityService service;

	@Test
	void test() {
		Community post = new Community();
		post.setMemberId(2L);
		post.setTitle("test");
		post.setOnlineType(false);
		post.setLocationInfo("test");
		post.setPeriod("test");
		post.setTeamSize(2);
		post.setThumbnail(null);
		
		
		service.register(post);
		
		System.out.println(post.getId());
	}

}
