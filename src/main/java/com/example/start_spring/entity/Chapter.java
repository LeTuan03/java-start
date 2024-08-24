package com.example.start_spring.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "chapters")
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    String id;

    @Column(name = "title")
    String title;

    @Column(name = "chapterNumber")
    Integer chapterNumber;

    @Column(name = "createdAt")
    LocalDateTime createdAt;

    String comicId;

    @OneToMany(mappedBy = "chapterId", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Page> pages;
}
