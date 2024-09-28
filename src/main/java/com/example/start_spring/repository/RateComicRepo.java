package com.example.start_spring.repository;

import com.example.start_spring.DTO.ComicListByUser;
import com.example.start_spring.DTO.RateComicDto;
import com.example.start_spring.entity.RateComic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RateComicRepo extends JpaRepository<RateComic, String> {

    @Query("SELECT new com.example.start_spring.DTO.RateComicDto(f) FROM RateComic f")
    List<RateComicDto> findAllReq();

    boolean existsByUserIdAndComicId(String userId, String comicId);

    @Query("SELECT new com.example.start_spring.DTO.RateComicDto(f) FROM RateComic f where f.comic.id = :comicId")
    List<RateComicDto> findAllByComicId(String comicId);

    @Query("SELECT new com.example.start_spring.DTO.ComicListByUser(r.comic.id, r.comic.title, r.comic.coverImage," +
            "r.comic.updatedAt, r.comic.author.name, r.comic.newestChapterNumber," +
            "sum(r.star)) FROM RateComic r group by r.comic.id ORDER BY sum(r.star) DESC limit 1")
    List<ComicListByUser> findTop10ByStar();

}
