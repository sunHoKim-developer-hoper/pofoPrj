package kr.co.pofo.pofoapiboot3.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@AutoConfigureTestDatabase(replace=Replace.NONE)
@MybatisTest
class CommunityTeamRepositoryTest {

	@Autowired
	private CommunityTeamRepository repository;
	
	@Test
	void test() {
		repository.insert(4L, 2L);
	}

}
