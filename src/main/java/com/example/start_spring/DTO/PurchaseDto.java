package com.example.start_spring.DTO;

import com.example.start_spring.entity.Account;
import com.example.start_spring.entity.Comic;
import com.example.start_spring.entity.ShoppingForm;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PurchaseDto {
    Account buyer;
    Account seller;
    Comic comic;
    ShoppingForm shoppingForm;
    Double price;
    Integer quantity;
    LocalDateTime purchaseDate;
    String purchaseNumber;
    String status;
    String note;
}
