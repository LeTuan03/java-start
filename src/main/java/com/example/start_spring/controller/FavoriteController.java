package com.example.start_spring.controller;

import com.example.start_spring.DTO.ApiResponse;
import com.example.start_spring.DTO.FavoriteDto;
import com.example.start_spring.entity.Favorites;
import com.example.start_spring.services.FavoritesService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/favorite")
public class FavoriteController {
    FavoritesService favoritesService;

    @GetMapping("/{userId}")
    ApiResponse<List<FavoriteDto>> getByUser(@PathVariable String userId) {
        return favoritesService.getByUser(userId);
    }

    @PostMapping("/{userId}/comic/{comicId}")
    ApiResponse<Favorites> create(@PathVariable String userId, @PathVariable String comicId) {
        return favoritesService.create(userId, comicId);
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> delete(@PathVariable String id) {
        return favoritesService.delete(id);
    }
}
