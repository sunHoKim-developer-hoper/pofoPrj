package kr.co.pofo.pofoapiboot3.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Community {
    private Long id;
    private Long memberId; 
    private String title;
    private Boolean onlineType;
    private String locationInfo; 
    private String period; 
    private Integer teamSize;
    private String thumbnail;    
    private Date regDate;
    private Date deleteDate;
    private Long hit;
    private int closeFlag;
}
