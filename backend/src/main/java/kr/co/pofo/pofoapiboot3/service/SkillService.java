package kr.co.pofo.pofoapiboot3.service;

import kr.co.pofo.pofoapiboot3.entity.Portfolio;
import kr.co.pofo.pofoapiboot3.entity.Skill;

import java.util.List;

public interface SkillService {
    List<Skill> getSkillsByPortfolioId(Integer portfolioId);

    boolean regPofoSkills(int id, String[] skills);

    

}
