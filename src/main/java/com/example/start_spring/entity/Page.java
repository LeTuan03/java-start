package com.example.start_spring.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "pages")
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    String id;

    @Column(name = "pageNumber")
    Integer pageNumber;

    @Column(name = "imageUrl")
    String imageUrl;

    String chapterId;

    @Column(name = "createdAt")
    LocalDateTime createdAt = LocalDateTime.now();
}
