package com.example.start_spring.controller;

import com.example.start_spring.DTO.PurchaseDto;
import com.example.start_spring.entity.Genres;
import com.example.start_spring.entity.Purchase;
import com.example.start_spring.services.PurchaseService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/purchase")
public class PurchaseController {
    PurchaseService purchaseService;


    @GetMapping
    List<Purchase> getAll() {
        return purchaseService.getAll();
    }

    @PostMapping
    ResponseEntity<Object> create(@RequestBody PurchaseDto request) {
        return purchaseService.createPurchase(request);
    }

    @PutMapping("/{id}")
    ResponseEntity<Object> update(@RequestBody PurchaseDto request, @PathVariable String id) {
            return purchaseService.updatePurchase(request, id);
    }

    @GetMapping("/{id}")
    ResponseEntity<Object> getById(@PathVariable String id) {
        return purchaseService.getById(id);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> delete(@PathVariable String id) {
        return purchaseService.delete(id);
    }


}
