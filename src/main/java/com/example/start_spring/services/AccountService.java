package com.example.start_spring.services;

import com.example.start_spring.DTO.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AccountService {
    List<AccountResponseDto> getAll();

    ResponseEntity<Object> create(AccountRequestDto request);

    ResponseEntity<Object> update(String id, AccountRequestDto request);

    ResponseEntity<Object> getById(String id);

    ResponseEntity<Object> delete(String id);

    ResponseEntity<Object> registerAuthor(String id, AuthorDto request);

    AccountResponseDto register(AccountRequestDto request);

    ApiResponse<AccountResponseDto> login(String username, String password);

    ApiResponse<AccountResponseDto> changePassword(ChangePasswordDto request);

}
