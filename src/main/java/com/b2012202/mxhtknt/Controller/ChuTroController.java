package com.b2012202.mxhtknt.Controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chutro")
@RequiredArgsConstructor
public class ChuTroController {
    @GetMapping("/sayHello")
    public String sayHelloChuTro(){
        return "Hello Chu Tro!";
    }
}
