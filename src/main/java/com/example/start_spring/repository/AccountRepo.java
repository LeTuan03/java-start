package com.example.start_spring.repository;

import com.example.start_spring.entity.Account;
import com.example.start_spring.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepo extends JpaRepository<Account, String> {


    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmailOrUsername(String email, String username);

    Optional<Account> findByUsernameAndPassword(String username, String password);

    boolean existsByAuthor(Author author);
}
