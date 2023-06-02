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
public class PortfolioContents {
    private Integer id;
    private Integer portfolioId;
    private String content;
    private String type;
    private Integer orders;
}
