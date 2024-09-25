package com.example.start_spring.DTO;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComicTopDto {
    String id;
    String title;
    String coverImage;
    LocalDateTime updatedAt;
    String authorName;
}
