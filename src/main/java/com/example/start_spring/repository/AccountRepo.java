package com.example.start_spring.repository;

import com.example.start_spring.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepo extends JpaRepository<Account, String> {

    Optional<Account> findByEmail (String email);
}