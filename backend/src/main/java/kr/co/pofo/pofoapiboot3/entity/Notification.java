package kr.co.pofo.pofoapiboot3.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Notification {
    private Integer id;
    private Integer typeId;
    private Integer fromMemberId;
    private Integer toMemberId;
    private Date regDate;
    private Integer portfolioId;
    private Integer communityId;
    private Integer clickFlag;
    private String image;
    private String nickname;
    private String url;
    private int acceptFlag;
}
