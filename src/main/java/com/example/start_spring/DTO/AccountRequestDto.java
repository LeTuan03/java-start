package com.example.start_spring.DTO;

import com.example.start_spring.entity.Account;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountRequestDto {
    String username;
    String password;
    String email;
    String phone;
    String avatar;
    String role;
    Boolean isActive;
}
