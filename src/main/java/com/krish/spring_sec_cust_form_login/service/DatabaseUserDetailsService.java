package com.krish.spring_sec_cust_form_login.service;

import com.krish.spring_sec_cust_form_login.entity.AppUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.krish.spring_sec_cust_form_login.repository.UserRepository;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public DatabaseUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        System.out.println("LOGIN ATTEMPT USERNAME = " + username);
        System.out.println(userRepository.findAll().size());
        AppUser appUser = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    System.out.println("USER NOT FOUND IN DB");
                    return new UsernameNotFoundException("User not found: " + username);
                });

        System.out.println("DB PASSWORD = " + appUser.getPassword());

        return User.builder()
                .username(appUser.getUsername())
                .password(appUser.getPassword())   // ✅ BCrypt FROM DB
                .roles(appUser.getRole())          // USER / ADMIN
                .disabled(!appUser.isEnabled())
                .build();
    }
}
