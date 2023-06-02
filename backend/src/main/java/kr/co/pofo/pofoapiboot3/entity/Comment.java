package kr.co.pofo.pofoapiboot3.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
@Data
public class Comment {

    private Integer id;
    private Integer memberId;
    private Integer portfolioId;
    private String content;
    private Date regDate;

    @Builder
    public Comment(Integer id, Integer memberId, Integer portfolioId, String content, Date regDate) {
        this.id = id;
        this.memberId = memberId;
        this.portfolioId = portfolioId;
        this.content = content;
        this.regDate = regDate;
    }
}