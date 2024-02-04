package com.b2012202.mxhtknt.DTO;

import com.b2012202.mxhtknt.Models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthenticationResponse {
    private User user;
    private String token;
    private String refreshToken;
}
