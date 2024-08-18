package com.example.start_spring.DTO;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorDto {
    String name;
    String phone;
    String address;
    String email;
    String type; //theo to chuc hoac tu do
    String status;
    LocalDate dateOfIssue;//ngay phat hanh
}
