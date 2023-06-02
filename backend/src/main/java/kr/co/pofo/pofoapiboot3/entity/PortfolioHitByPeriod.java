package kr.co.pofo.pofoapiboot3.entity;

import lombok.Data;

@Data
public class PortfolioHitByPeriod {
    private Integer id;
    private Integer portfolioId;
    private Integer weeklyHit;
    private Integer monthlyHit;
}
