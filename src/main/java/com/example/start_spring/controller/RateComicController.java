package com.example.start_spring.controller;

import com.example.start_spring.DTO.ApiResponse;
import com.example.start_spring.entity.RateComic;
import com.example.start_spring.services.RateComicService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/rate-comic")
public class RateComicController {
    RateComicService rateComicService;

    @GetMapping()
    ApiResponse<Object> getAll() {
        return rateComicService.getAll();
    }

    @PostMapping("/{userId}/comic/{comicId}/{star}")
    ApiResponse<RateComic> create(@PathVariable String userId, @PathVariable String comicId, @PathVariable Integer star) {
        return rateComicService.create(userId, comicId, star);
    }

}
