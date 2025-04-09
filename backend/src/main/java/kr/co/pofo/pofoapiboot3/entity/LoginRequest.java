package kr.co.pofo.pofoapiboot3.entity;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
