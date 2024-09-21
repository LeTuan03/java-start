package com.example.start_spring.entity;

import com.example.start_spring.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    String id;

    @Column(name = "username")
    String username;

    @Column(name = "password")
    String password;

    @Column(name = "email")
    String email;

    @Column(name = "phone")
    String phone;

    @Column(name = "avatar")
    String avatar;

    @Column(name = "fullName")
    String fullName;

    @Column(name = "createdAt")
    LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "address")
    String address;

    @Column(name = "role")
    String role = RoleEnum.USER.name();

    @Column(name = "isActive")
    Boolean isActive = true;

    @OneToOne
    @JoinColumn(name = "authorId")
    Author author;

    @Column(name = "isAllowRegister")
    Boolean isAllowRegister = true;

    @PrePersist
    protected void onCreate() {
        this.isAllowRegister = true;
    }
}
