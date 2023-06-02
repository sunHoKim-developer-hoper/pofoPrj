package kr.co.pofo.pofoapiboot3.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	private Integer id;
    private String email;
    private String pwd;
    private String nickname;
    private String url;
    private String image;
}
