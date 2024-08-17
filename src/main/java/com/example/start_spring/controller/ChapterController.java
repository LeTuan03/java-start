package com.example.start_spring.controller;

import com.example.start_spring.DTO.ChapterRequest;
import com.example.start_spring.entity.Chapter;
import com.example.start_spring.entity.Page;
import com.example.start_spring.services.ChapterService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/chapter")
public class ChapterController {
    ChapterService chapterService;

    @GetMapping
    List<Chapter> getAll() {
        return chapterService.getAll();
    }

    @PostMapping
    ResponseEntity<Object> create(@RequestBody ChapterRequest chapter) {
        return chapterService.create(chapter);
    }

    @PutMapping
    ResponseEntity<Object> update(@RequestBody ChapterRequest chapter) {
        return chapterService.update(chapter);
    }

    @GetMapping("/{id}")
    ResponseEntity<Object> getById(@PathVariable String id) {
        return chapterService.getById(id);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> delete(@PathVariable String id) {
        return chapterService.delete(id);
    }

}
