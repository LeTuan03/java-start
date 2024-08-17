package com.example.start_spring.repository;

import com.example.start_spring.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapterRepo extends JpaRepository<Chapter, String> {
}
