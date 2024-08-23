package com.example.start_spring.repository;

import com.example.start_spring.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PurchaseRepo extends JpaRepository<Purchase, String> {
    @Query("SELECT COUNT(p) FROM Purchase p")
    int getNextIdFromDatabase();
}
