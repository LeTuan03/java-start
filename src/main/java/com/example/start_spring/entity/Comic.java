package com.example.start_spring.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "comics")
public class Comic {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    String id;

    @Column(name = "title")
    String title;

    @Column(name = "description")
    String description;

    @Column(name = "coverImage")
    String coverImage;

    @Column(name = "createdAt")
    LocalDate createdAt = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "authorId")
    Author author;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "comic_genres",
            joinColumns = @JoinColumn(name = "comic_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    Set<Genres> genres;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "comicId", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Chapter> chapters;

}
