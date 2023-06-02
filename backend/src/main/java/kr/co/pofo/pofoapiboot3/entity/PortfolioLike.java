package kr.co.pofo.pofoapiboot3.entity;

import lombok.Data;

import java.util.Date;

@Data
public class PortfolioLike {
    private Integer memberId;
    private Integer portfolioId;
    private Date likedDate;
}
