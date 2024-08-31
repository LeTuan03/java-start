package com.example.start_spring.controller;

import com.example.start_spring.DTO.PageDto;
import com.example.start_spring.entity.Page;
import com.example.start_spring.services.PageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @PutMapping("/image")
    ResponseEntity<Object> updateImage(@RequestParam("id") String id, @RequestParam("imageUrl")MultipartFile imageUrl) throws IOException {
        return pageService.updateImage(id, imageUrl);
    }

    @PostMapping("/image-create")
    ResponseEntity<Object> createImage(@RequestParam("imageUrl") MultipartFile imageUrl) throws IOException {
        return pageService.createImage(imageUrl);
    }
}
