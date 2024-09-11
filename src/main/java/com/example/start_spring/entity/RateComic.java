package com.example.start_spring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rateComic")
public class RateComic {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    String id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    Account user;

    @ManyToOne
    @JoinColumn(name = "comic_id", referencedColumnName = "id")
    Comic comic;

    @Column(name = "star")
    Integer star;

    @Column(name = "createdAt")
    LocalDateTime createdAt = LocalDateTime.now();
}
