package kr.co.pofo.pofoapiboot3.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import kr.co.pofo.pofoapiboot3.entity.Community;

@AutoConfigureTestDatabase(replace=Replace.NONE)
@MybatisTest
class CommunityRepositoryTest {

	@Autowired
	private CommunityRepository repository;
	
	@Test
	void test() {
		System.out.println(repository.findIdByMemberId(26L));
	}
	
	// @Test
	// void test() {
	// 	List<Community> list = repository.findAll();
	// 	System.out.println(list);
	// }

	//@Test
	// void test() {
	// 	Community post = new Community();
	// 	post.setMemberId(2L);
	// 	post.setTitle("하이루");
	// 	post.setOnlineType(true);
	// 	post.setPeriod("6월 동안");
	// 	post.setTeamSize(6);

	// 	repository.insert(post);
	// }

//	@Test
//	void test() {
//		Community community = repository.findById(9L);
//		System.out.println(community);
//	}

}
