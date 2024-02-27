package com.b2012202.mxhtknt.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String numberPhone;
    private boolean gender;
    private Date dateOfBirth;
    private String cccd;
    private String avt;
}
