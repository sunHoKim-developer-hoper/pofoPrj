package kr.co.pofo.pofoapiboot3.repository;

import java.util.List;
import java.util.Map;

import kr.co.pofo.pofoapiboot3.entity.Skill;
import kr.co.pofo.pofoapiboot3.entity.UsedSkill;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SkillRepository {
//	Skill
	List<Skill> findAll();

	void insertSkill(Skill skill);

	void updateSkill(Skill skill);

	void deleteSkill(Skill skill);

//	UsedSkill
	List<Skill> findSkillsByPortfolioId(Integer portfolioId);


    // void insertUsedSkill(Integer id, String skill);

	void insertUsedSkill(Map<String, Object> paramMap);

    




}
