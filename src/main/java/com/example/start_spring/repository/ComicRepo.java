package com.example.start_spring.repository;

import com.example.start_spring.entity.Comic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComicRepo extends JpaRepository<Comic, String> {
}
