package com.example.start_spring.services;

import com.example.start_spring.DTO.AuthorDto;
import com.example.start_spring.entity.Author;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AuthorService {
    List<Author> getAll();

    ResponseEntity<Object> create(AuthorDto request);

    ResponseEntity<Object> update(AuthorDto request, String id);

    ResponseEntity<Object> getById(String id);

    ResponseEntity<Object> delete(String id);
}
