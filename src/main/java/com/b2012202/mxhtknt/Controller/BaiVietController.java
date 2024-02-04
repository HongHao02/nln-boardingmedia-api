package com.b2012202.mxhtknt.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
public class BaiVietController {

    @GetMapping("/sayHello")
    public String sayHello(){
        return "Hello! Get all post";
    }
}
