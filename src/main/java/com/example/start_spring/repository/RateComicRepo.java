package com.example.start_spring.repository;

import com.example.start_spring.DTO.RateComicDto;
import com.example.start_spring.entity.RateComic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RateComicRepo extends JpaRepository<RateComic, String> {

    @Query("SELECT new com.example.start_spring.DTO.RateComicDto(f) FROM RateComic f")
    List<RateComicDto> findAllReq();

}
