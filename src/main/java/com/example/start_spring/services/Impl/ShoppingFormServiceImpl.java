package com.example.start_spring.services.Impl;

import com.example.start_spring.entity.Page;
import com.example.start_spring.entity.ShoppingForm;
import com.example.start_spring.repository.ShoppingFormRepo;
import com.example.start_spring.services.ShoppingFormService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShoppingFormServiceImpl implements ShoppingFormService {

    ShoppingFormRepo shoppingFormRepo;

    @Override
    public List<ShoppingForm> getAll() {
        return shoppingFormRepo.findAll();
    }

    @Override
    public ResponseEntity<Object> create(ShoppingForm request) {
        try {
            ShoppingForm entity = new ShoppingForm();

            entity.setName(request.getName());

            shoppingFormRepo.save(entity);

            return new ResponseEntity<>(entity, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Object> update(ShoppingForm request) {
        try {
            Optional<ShoppingForm> isExits = shoppingFormRepo.findById(request.getId());
            if (isExits.isPresent()) {
                ShoppingForm entity = isExits.get();

                entity.setName(request.getName());

                shoppingFormRepo.save(entity);

                return new ResponseEntity<>(entity, HttpStatus.OK);
            }
            return new ResponseEntity<>("Có lỗi xảy ra", HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Object> getById(String id) {
        try {
            Optional<ShoppingForm> isExits = shoppingFormRepo.findById(id);
            if (isExits.isPresent()) {
                return new ResponseEntity<>(isExits, HttpStatus.OK);
            }
            return new ResponseEntity<>("Có lỗi xảy ra", HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Object> delete(String id) {
        Optional<ShoppingForm> isExits = shoppingFormRepo.findById(id);

        if (isExits.isPresent()) {
            shoppingFormRepo.deleteById(id);

            return new ResponseEntity<>("Xóa thành công", HttpStatus.OK);
        }
        throw new RuntimeException("Không tìm thấy");
    }
}
