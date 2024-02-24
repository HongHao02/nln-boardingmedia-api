package com.b2012202.mxhtknt.Request;

import lombok.Data;

import java.util.Date;

@Data
public class ChangeInfoRequest {
    private String firstName;
    private String lastName;
    private String numberPhone;
    private boolean gender;
    private String dateOfBirth;
    private String cccd;
}
