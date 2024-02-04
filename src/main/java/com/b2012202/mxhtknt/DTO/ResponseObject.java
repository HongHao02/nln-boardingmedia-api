package com.b2012202.mxhtknt.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseObject {
    private String status;
    private String message;
    //Ly do data co kieu la Object do du lieu tra ve co nhieu dang, ta chua biet duoc dang nao
    private Object data;

}
