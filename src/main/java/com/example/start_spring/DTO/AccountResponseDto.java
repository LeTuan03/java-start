package com.example.start_spring.DTO;

import com.example.start_spring.entity.Account;
import com.example.start_spring.entity.Author;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountResponseDto {
    String id;
    String username;
    String email;
    String phone;
    String avatar;
    String role;
    Boolean isActive;
    Author author;

    Boolean isAllowRegister;

    public AccountResponseDto(Account account) {
        this.id = account.getId();
        this.username = account.getUsername();
        this.email = account.getEmail();
        this.phone = account.getPhone();
        this.avatar = account.getAvatar();
        this.role = account.getRole();
        this.isActive = account.getIsActive();
        this.author = account.getAuthor();
        this.isAllowRegister = account.getIsAllowRegister();
    }
}
