package com.example.start_spring.DTO;

import com.example.start_spring.entity.Account;
import com.example.start_spring.entity.Comic;
import com.example.start_spring.entity.Favorites;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FavoriteDto {
    String id;
    Comic comic;
    Account user;
    LocalDateTime createdAt;

    public FavoriteDto(Favorites entity) {
        this.id = entity.getId();
        this.createdAt = entity.getCreatedAt();

        if (Objects.nonNull(entity.getComic())){
            this.comic = entity.getComic();
        }

        if(Objects.nonNull(entity.getUser())){
            this.user = entity.getUser();
        }
    }
}
