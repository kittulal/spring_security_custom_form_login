package com.krish.spring_sec_cust_form_login.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/greet")
    public String greet() {
        return "Hello World!";
    }

    @GetMapping("/user/greet/{userName}")
    @PreAuthorize("hasAuthority('USER)")
    public String greetUser(@PathVariable String userName) {
        return "Hello "+userName+"!";
    }

    @GetMapping("/admin/greet")
    @PreAuthorize("hasAuthority('ADMIN)")
    public String greetAdmin() {
        return "Hello ADMIN!";
    }
}
