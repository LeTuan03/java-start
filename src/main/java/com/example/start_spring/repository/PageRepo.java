package com.example.start_spring.repository;

import com.example.start_spring.entity.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageRepo extends JpaRepository<Page, String> {
}
