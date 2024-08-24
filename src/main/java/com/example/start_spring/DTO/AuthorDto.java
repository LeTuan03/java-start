package com.example.start_spring.DTO;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorDto {
    String id;
    String name;
    String phone;
    String address;
    String email;
    Integer type; //theo to chuc hoac tu do
    Integer status;
    LocalDateTime dateOfIssue;//ngay phat hanh
}
