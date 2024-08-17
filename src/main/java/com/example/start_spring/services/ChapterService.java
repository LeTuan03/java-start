package com.example.start_spring.services;

import com.example.start_spring.DTO.ChapterRequest;
import com.example.start_spring.entity.Chapter;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ChapterService {
    List<Chapter> getAll();

    ResponseEntity<Object> create(ChapterRequest chapter);

    ResponseEntity<Object> update(ChapterRequest chapter);

    ResponseEntity<Object> getById(String id);

    ResponseEntity<Object> delete(String id);
}
