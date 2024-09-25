package com.example.start_spring.DTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComicListByUser {
    public ComicListByUser(String id, String title, String coverImage, LocalDateTime updatedAt, String authorName, Integer newestChapterNumber) {
        this.id = id;
        this.title = title;
        this.coverImage = coverImage;
        this.updatedAt = updatedAt;
        this.authorName = authorName;
        this.newestChapterNumber = newestChapterNumber;
    }

    String id;
    String title;
    String coverImage;
    LocalDateTime updatedAt;
    String authorName;
    Integer newestChapterNumber;
}
