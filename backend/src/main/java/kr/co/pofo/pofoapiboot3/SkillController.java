package kr.co.pofo.pofoapiboot3;

import kr.co.pofo.pofoapiboot3.entity.Skill;
import kr.co.pofo.pofoapiboot3.service.SkillService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SkillController {
    private final SkillService service;

    public SkillController(SkillService service) {
        this.service = service;
    }

    @GetMapping("pofo/{portfolioId}/skills")
    public List<Skill> getSkillsByPortfolioId(@PathVariable Integer portfolioId) {
        return service.getSkillsByPortfolioId(portfolioId);
    }
}
