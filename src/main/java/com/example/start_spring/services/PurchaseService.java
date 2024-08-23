package com.example.start_spring.services;

import com.example.start_spring.DTO.PurchaseDto;
import com.example.start_spring.entity.Purchase;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PurchaseService {

    List<Purchase> getAll();

    ResponseEntity<Object> createPurchase(PurchaseDto request);

    ResponseEntity<Object> updatePurchase(PurchaseDto request, String id);

    ResponseEntity<Object> getById(String id);

    ResponseEntity<Object> delete(String id);

}
