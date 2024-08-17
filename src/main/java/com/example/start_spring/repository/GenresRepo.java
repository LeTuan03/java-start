package com.example.start_spring.repository;

import com.example.start_spring.entity.Genres;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenresRepo extends JpaRepository<Genres, String> {
}
