package com.example.start_spring.repository;

import com.example.start_spring.DTO.FavoriteDto;
import com.example.start_spring.entity.Favorites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritesRepo extends JpaRepository<Favorites, String> {

    @Query("SELECT new com.example.start_spring.DTO.FavoriteDto(f) " +
            "FROM Favorites f WHERE f.user.id = :userId")
    List<FavoriteDto> findByUserId(String userId);

    boolean existsByUserIdAndComicId(String userId, String comicId);
}
