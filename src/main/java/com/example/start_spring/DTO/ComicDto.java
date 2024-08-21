package com.example.start_spring.DTO;

import com.example.start_spring.entity.Author;
import com.example.start_spring.entity.Chapter;
import com.example.start_spring.entity.Comic;
import com.example.start_spring.entity.Genres;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComicDto {
    String id;
    String title;
    String description;
    Author author;
    String coverImage;
    Set<Genres> genres;
    Set<Chapter> chapters;
}
