package com.example.start_spring.services;

import com.example.start_spring.DTO.ApiResponse;
import com.example.start_spring.DTO.FavoriteDto;
import com.example.start_spring.entity.Favorites;

import java.util.List;

public interface FavoritesService {
    ApiResponse<Favorites> create(String userId, String comicId);
    ApiResponse<String> delete(String id);

    ApiResponse<List<FavoriteDto>> getByUser(String userId);
}
