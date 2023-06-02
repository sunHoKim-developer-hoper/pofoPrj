package kr.co.pofo.pofoapiboot3.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Recruit {
    private Integer id;
    private Integer memberId;
    private String title;
    private String projectInfo;
    private String coopInfo;
    private Integer coopType;
    private Integer teamSize;
    private String thumbnail;
    private Date regDate;
    private Date deleteDate;
    private Integer hit;
}
