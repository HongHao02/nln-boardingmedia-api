package com.b2012202.mxhtknt.DTO;

import lombok.Data;

@Data
public class SignUpRequest {
    private String username;
    private String password;
    private String role;
}
