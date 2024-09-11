package com.example.start_spring.DTO;

import com.example.start_spring.entity.RateComic;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RateComicDto {
    String id;
    Integer star;
    LocalDateTime createdAt;

    String comicId;
    String userId;

    public RateComicDto(RateComic entity) {
        this.id = entity.getId();
        this.star = entity.getStar();
        this.createdAt = entity.getCreatedAt();

        if(Objects.nonNull(entity.getComic())) {
            this.comicId = entity.getComic().getId();
        }

        if(Objects.nonNull(entity.getUser())){
            this.userId = entity.getUser().getId();
        }
    }
}
