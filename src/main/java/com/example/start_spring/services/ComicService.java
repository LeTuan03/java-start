package com.example.start_spring.services;

import com.example.start_spring.entity.Comic;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

public interface ComicService {
    List<Comic> getAll();

    ResponseEntity<Object> create(Comic comic);

    ResponseEntity<Object> update(Comic genres);

    ResponseEntity<Object> getById(String id);

    ResponseEntity<Object> delete(String id);
}
