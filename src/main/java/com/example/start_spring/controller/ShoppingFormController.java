package com.example.start_spring.controller;

import com.example.start_spring.DTO.PageDto;
import com.example.start_spring.entity.Page;
import com.example.start_spring.entity.ShoppingForm;
import com.example.start_spring.services.PageService;
import com.example.start_spring.services.ShoppingFormService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/shopping-form")
public class ShoppingFormController {
    ShoppingFormService shoppingFormService;

    @GetMapping
    List<ShoppingForm> getAll() {
        return shoppingFormService.getAll();
    }

    @PostMapping
    ResponseEntity<Object> create(@RequestBody ShoppingForm shoppingForm) {
        return shoppingFormService.create(shoppingForm);
    }

    @PutMapping
    ResponseEntity<Object> update(@RequestBody ShoppingForm shoppingForm) {
        return shoppingFormService.update(shoppingForm);
    }

    @GetMapping("/{id}")
    ResponseEntity<Object> getById(@PathVariable String id) {
        return shoppingFormService.getById(id);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> delete(@PathVariable String id) {
        return shoppingFormService.delete(id);
    }


}
