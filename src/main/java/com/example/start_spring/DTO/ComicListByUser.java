package com.example.start_spring.DTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComicListByUser {
    String id;
    String title;
    String coverImage;
    LocalDateTime updatedAt;
}
