package kr.co.pofo.pofoapiboot3.entity;

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
public class CommunityTeam {
    private Long id;
    private Long communityId; 
    private Long memberId; 
    private Integer participateFlag;
    private int registerFlag;
}
