package com.example.start_spring.services;

import com.example.start_spring.DTO.PageDto;
import com.example.start_spring.entity.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PageService {
    List<Page> getAll();

    ResponseEntity<Object> create(PageDto page);

    ResponseEntity<Object> update(PageDto page, String id);

    ResponseEntity<Object> getById(String id);

    ResponseEntity<Object> delete(String id);

    ResponseEntity<Object> updateMulti(List<PageDto> pages, String chapterId);

    ResponseEntity<Object> updateImage(String id, MultipartFile imageUrl) throws IOException;

    ResponseEntity<Object> createImage(MultipartFile imageUrl) throws IOException;
}
