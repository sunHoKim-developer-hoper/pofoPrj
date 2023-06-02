package kr.co.pofo.pofoapiboot3.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CommentView {
    private Integer id;
    private Integer memberId;
    private Integer portfolioId;
    private String content;
    private Date regDate;
    private String memberNickname;
    private String memberImage;
}
