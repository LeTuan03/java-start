package com.example.start_spring.repository;

import com.example.start_spring.entity.ShoppingForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingFormRepo extends JpaRepository<ShoppingForm, String> {
}
