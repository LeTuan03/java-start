package com.example.start_spring.services;

import com.example.start_spring.DTO.ApiResponse;
import com.example.start_spring.DTO.ComicDto;
import com.example.start_spring.DTO.ComicListByUser;
import com.example.start_spring.entity.Comic;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

public interface ComicService {
    List<Comic> getAll();

    ResponseEntity<Object> create(ComicDto comic);

    ResponseEntity<Object> update(ComicDto comic, String id);

    ResponseEntity<Object> getById(String id);

    ResponseEntity<Object> delete(String id);

    List<ComicListByUser> getAllByUser();

    ResponseEntity<Object> like(String id);

    ApiResponse<List<ComicListByUser>> getListComicOrderByUpdatedAt();
}
