package com.example.start_spring.controller;

import com.example.start_spring.DTO.AccountRequestDto;
import com.example.start_spring.DTO.AccountResponseDto;
import com.example.start_spring.DTO.AuthorDto;
import com.example.start_spring.entity.Author;
import com.example.start_spring.services.AuthorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/author")
public class AuthorController {
    AuthorService authorService;

    @GetMapping
    List<Author> getAll() {
        return authorService.getAll();
    }

    @PostMapping
    ResponseEntity<Object> create(@RequestBody AuthorDto request) {
        return authorService.create(request);
    }

    @PutMapping("/{id}")
    ResponseEntity<Object> update(@PathVariable String id, @RequestBody AuthorDto request) {
        return authorService.update(request, id);
    }

    @GetMapping("/{id}")
    ResponseEntity<Object> getById(@PathVariable String id) {
        return authorService.getById(id);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> delete(@PathVariable String id) {
        return authorService.delete(id);
    }
}