package com.example.start_spring.DTO;

import com.example.start_spring.entity.Author;
import com.example.start_spring.entity.Chapter;
import com.example.start_spring.entity.Genres;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetComicDto {
    String id;
    String title;
    String description;
    Author author;
    String coverImage;
    Set<Genres> genres;
    Set<Chapter> chapters;
    Integer likeCount;
    Integer viewCount;
    LocalDate createdAt;
    Integer numFollow;
    Integer status;
}
