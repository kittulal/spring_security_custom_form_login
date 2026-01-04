package com.krish.spring_sec_cust_form_login.controller;

import com.krish.spring_sec_cust_form_login.dto.AuthRequest;
import com.krish.spring_sec_cust_form_login.dto.AuthResponse;
import com.krish.spring_sec_cust_form_login.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public AuthResponse authenticateUser(@RequestBody AuthRequest authRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );
        User user = (User) authentication.getPrincipal();

        String role = user.getAuthorities().iterator().next().getAuthority();

        String token = jwtService.generateToken(user);

        return new AuthResponse(
                user.getUsername(),
                Arrays.asList(role),
                token
        );
    }
}
