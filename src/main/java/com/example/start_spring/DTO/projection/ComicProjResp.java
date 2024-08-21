package com.example.start_spring.DTO.projection;

import com.example.start_spring.entity.Chapter;
import com.example.start_spring.entity.Genres;
import com.example.start_spring.services.ChapterService;

import java.time.LocalDateTime;
import java.util.List;

public interface ComicProjResp {
    String getId();

    String getTitle();

    String getDescription();

    String getCoverImage();

    //    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "")
    LocalDateTime getCreatedAt();

    String getAuthorId();

    String getAuthorName();

    List<Genres> getGenres();

    List<Chapter> getChapters();

}
