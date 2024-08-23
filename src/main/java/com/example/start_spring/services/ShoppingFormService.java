package com.example.start_spring.services;

import com.example.start_spring.entity.ShoppingForm;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ShoppingFormService {
    List<ShoppingForm> getAll();

    ResponseEntity<Object> create(ShoppingForm request);

    ResponseEntity<Object> update(ShoppingForm request);

    ResponseEntity<Object> getById(String id);

    ResponseEntity<Object> delete(String id);
}
