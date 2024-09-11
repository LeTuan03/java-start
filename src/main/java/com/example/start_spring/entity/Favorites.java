package com.example.start_spring.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "favorites")
public class Favorites {
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

    @Column(name = "createdAt")
    LocalDateTime createdAt = LocalDateTime.now();
}
