package com.example.start_spring.services;

import com.example.start_spring.DTO.AccountRequestDto;
import com.example.start_spring.DTO.AccountResponseDto;
import com.example.start_spring.DTO.AuthorDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AccountService {
    List<AccountResponseDto> getAll();

    ResponseEntity<Object> create(AccountRequestDto request);

    ResponseEntity<Object> update(String id, AccountRequestDto request);

    ResponseEntity<Object> getById(String id);

    ResponseEntity<Object> delete(String id);

    ResponseEntity<Object> registerAuthor(String id, AuthorDto request);
}
