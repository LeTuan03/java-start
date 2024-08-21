package com.example.start_spring.controller;

import com.example.start_spring.DTO.ComicDto;
import com.example.start_spring.entity.Comic;
import com.example.start_spring.entity.Genres;
import com.example.start_spring.services.ComicService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/comic")
public class ComicController {
    ComicService comicService;

    @GetMapping
    List<Comic> getAll() {
        return comicService.getAll();
    }

    @PostMapping
    ResponseEntity<Object> create(@RequestBody ComicDto comic) {
        return comicService.create(comic);
    }

    @PutMapping("/{id}")
    ResponseEntity<Object> update(@RequestBody ComicDto comic, @PathVariable String id) {
        return comicService.update(comic, id);
    }

    @GetMapping("/{id}")
    ResponseEntity<Object> getById(@PathVariable String id) {
        return comicService.getById(id);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> deleteById(@PathVariable String id) {
        return comicService.delete(id);
    }
}
