package com.example.start_spring.controller;

import com.example.start_spring.DTO.AccountRequestDto;
import com.example.start_spring.DTO.AccountResponseDto;
import com.example.start_spring.services.AccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/account")
public class AccountController {
    AccountService accountService;

    @GetMapping
    List<AccountResponseDto> getAll() {
        return accountService.getAll();
    }

    @PostMapping
    ResponseEntity<Object> create(@RequestBody AccountRequestDto request) {
        return accountService.create(request);
    }

    @PutMapping("/{id}")
    ResponseEntity<Object> update(@PathVariable String id, @RequestBody AccountRequestDto request) {
        return accountService.update(id, request);
    }

    @GetMapping("/{id}")
    ResponseEntity<Object> getById(@PathVariable String id) {
        return accountService.getById(id);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> delete(@PathVariable String id) {
        return accountService.delete(id);
    }
}
