package com.example.start_spring.controller;

import com.example.start_spring.entity.Genres;
import com.example.start_spring.services.GenresService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/genres")
public class GenresController {
    GenresService genresService;

    @GetMapping
    List<Genres> getAll() {
        return genresService.getAll();
    }

    @PostMapping
    ResponseEntity<Object> create(@RequestBody Genres genres) {
        return genresService.create(genres);
    }

    @PutMapping()
    ResponseEntity<Object> update(@RequestBody Genres genres) {
        return genresService.update(genres);
    }

    @GetMapping("/{id}")
    ResponseEntity<Object> getById(@PathVariable String id) {
        return genresService.getById(id);
    }

    @DeleteMapping("/{id}")
    String deleteById(@PathVariable String id) {
        return genresService.delete(id);
    }
}
