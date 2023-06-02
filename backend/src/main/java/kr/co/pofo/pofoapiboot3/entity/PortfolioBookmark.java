package kr.co.pofo.pofoapiboot3.entity;

import lombok.Data;

@Data
public class PortfolioBookmark {
    private Integer id;
    private int portfolioId;
    private int memberId;
}
