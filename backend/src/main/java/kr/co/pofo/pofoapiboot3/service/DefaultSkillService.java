package kr.co.pofo.pofoapiboot3.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.pofo.pofoapiboot3.entity.Portfolio;
import kr.co.pofo.pofoapiboot3.entity.Skill;
import kr.co.pofo.pofoapiboot3.repository.SkillRepository;

@Service
public class DefaultSkillService implements SkillService {

	@Autowired
	private SkillRepository repository;

	@Override
	public List<Skill> getSkillsByPortfolioId(Integer portfolioId) {
		return repository.findSkillsByPortfolioId(portfolioId);
	}

	// @Override
	// public boolean regPofo(Portfolio pofo, String[] skills) {

	// 	Integer id = pofo.getId();
	// 	for (String skill : skills) {
	// 		repository.insertUsedSkill(id, skill);
	// 	}
	// 	return true;

	// }
	
	@Override
	public boolean regPofoSkills(int id, String[] skills) {

		// skills가 null인 경우 빈 배열로 초기화
		if (skills == null) {
			skills = new String[0];
		}

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("id", id);
		paramMap.put("skills", skills);
		repository.insertUsedSkill(paramMap);
		return true;

	}
}
