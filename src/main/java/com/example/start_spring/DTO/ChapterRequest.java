package com.example.start_spring.DTO;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChapterRequest {
    String id;
    String title;
    int chapterNumber;
    String comicId;
    List<PageDto> pages;
}
