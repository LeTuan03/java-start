package com.example.start_spring.services;

import com.example.start_spring.DTO.ApiResponse;
import com.example.start_spring.DTO.ComicListByUser;
import com.example.start_spring.entity.RateComic;

import java.util.List;

public interface RateComicService {
    ApiResponse<RateComic> create(String userId, String comicId, Integer star);
    ApiResponse<Object> getAll();

    ApiResponse<List<ComicListByUser>> getTop10HighComicRate();
}
