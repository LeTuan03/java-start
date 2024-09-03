package com.example.start_spring.controller;

import com.example.start_spring.DTO.AccountRequestDto;
import com.example.start_spring.DTO.AccountResponseDto;
import com.example.start_spring.DTO.ApiResponse;
import com.example.start_spring.DTO.AuthorDto;
import com.example.start_spring.enums.CodeEnum;
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

    @PutMapping("/{accountId}/register-author")
    ResponseEntity<Object> registerAuthor(@PathVariable String accountId, @RequestBody AuthorDto authorDto) {
        return accountService.registerAuthor(accountId, authorDto);
    }

    @PostMapping("/register")
    ApiResponse<AccountResponseDto> register(@RequestBody AccountRequestDto request) {
        AccountResponseDto accountResponseDto = accountService.register(request);
        ApiResponse<AccountResponseDto> apiResponse = new ApiResponse();
        apiResponse.setCode(CodeEnum.REGISTER_SUCCESS.getCode());
        apiResponse.setMessage(CodeEnum.REGISTER_SUCCESS.getMessage());
        apiResponse.setData(accountResponseDto);
        return apiResponse;
    }

    @PostMapping("/login")
    ApiResponse<AccountResponseDto> login(@RequestParam String username, @RequestParam String password) {
        return accountService.login(username, password);
    }
}
