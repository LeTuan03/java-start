package com.example.start_spring.services;

import com.example.start_spring.entity.Genres;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface GenresService {
    List<Genres> getAll();

    ResponseEntity<Object> create(Genres genres);

    ResponseEntity<Object> update(Genres genres);

    ResponseEntity<Object> getById(String id);

    String delete(String id);
}
