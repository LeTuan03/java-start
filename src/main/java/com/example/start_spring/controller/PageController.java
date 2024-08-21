package com.example.start_spring.controller;

import com.example.start_spring.DTO.PageDto;
import com.example.start_spring.entity.Page;
import com.example.start_spring.services.PageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/page")
public class PageController {
    PageService pageService;

    @GetMapping
    List<Page> getAll() {
        return pageService.getAll();
    }

    @PostMapping
    ResponseEntity<Object> create(@RequestBody PageDto page) {
        return pageService.create(page);
    }

    @PutMapping("/{id}")
    ResponseEntity<Object> update(@RequestBody PageDto page, @PathVariable String id) {
        return pageService.update(page, id);
    }

    @GetMapping("/{id}")
    ResponseEntity<Object> getById(@PathVariable String id) {
        return pageService.getById(id);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> delete(@PathVariable String id) {
        return pageService.delete(id);
    }

    @PutMapping("/multi/{chapterId}")
    ResponseEntity<Object> updateMulti(@PathVariable String chapterId, @RequestBody List<PageDto> pages) {
        return pageService.updateMulti(pages, chapterId);
    }
}
