package kr.co.pofo.pofoapiboot3.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Activities {
    private int hited;
    private int liked;
    private int collected;
    private int follower;
    private int following;
    
}
