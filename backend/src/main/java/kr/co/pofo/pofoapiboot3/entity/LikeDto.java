package kr.co.pofo.pofoapiboot3.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LikeDto {
    private Integer memberId;
    private Integer portfolioId;
}
