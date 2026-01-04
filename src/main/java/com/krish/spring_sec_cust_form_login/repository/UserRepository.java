package com.krish.spring_sec_cust_form_login.repository;

import com.krish.spring_sec_cust_form_login.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM mydb.users where username=:username")
    Optional<AppUser> findByUsername(String username);
}