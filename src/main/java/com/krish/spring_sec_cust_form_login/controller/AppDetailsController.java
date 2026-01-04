package com.krish.spring_sec_cust_form_login.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppDetailsController {
    @GetMapping("/app/welcome")
    public String welcome() {
        return "Hello World";
    }
}
