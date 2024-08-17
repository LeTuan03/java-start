package com.example.start_spring.DTO;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageDto {
    String id;
    Integer pageNumber;
    String imageUrl;
    String chapterId;
}
