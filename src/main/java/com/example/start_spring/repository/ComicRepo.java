package com.example.start_spring.repository;

import com.example.start_spring.DTO.ComicListByUser;
import com.example.start_spring.entity.Comic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ComicRepo extends JpaRepository<Comic, String> {
    @Query("SELECT new com.example.start_spring.DTO.ComicListByUser(c.id, c.title, c.coverImage, c.updatedAt, c.author.name, c.newestChapterNumber) FROM Comic c ORDER BY c.updatedAt DESC")
    List<ComicListByUser> getListComicOrderByUpdatedAt();
}
