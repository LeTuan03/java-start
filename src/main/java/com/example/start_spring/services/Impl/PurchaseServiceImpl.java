package com.example.start_spring.services.Impl;

import com.example.start_spring.DTO.PurchaseDto;
import com.example.start_spring.entity.Purchase;
import com.example.start_spring.repository.AccountRepo;
import com.example.start_spring.repository.PurchaseRepo;
import com.example.start_spring.services.PurchaseService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PurchaseServiceImpl implements PurchaseService {
    PurchaseRepo purchaseRepo;

    private String generateUniqueId() {
        String prefix = "PUR-";
        int nextId = purchaseRepo.getNextIdFromDatabase();
        return prefix + String.format("%06d", nextId);
    }

    void setValueDtos(Purchase entity, PurchaseDto request) {
        entity.setBuyer(request.getBuyer());
        entity.setSeller(request.getSeller());
        entity.setComic(request.getComic());
        entity.setShoppingForm(request.getShoppingForm());
        entity.setPrice(request.getPrice());
        entity.setPurchaseDate(LocalDateTime.now());
        entity.setPurchaseNumber(this.generateUniqueId());
        entity.setStatus(request.getStatus());
        entity.setNote(request.getNote());
        entity.setQuantity(request.getQuantity());
    }

    @Override
    public List<Purchase> getAll() {
        return purchaseRepo.findAll();
    }

    @Override
    public ResponseEntity<Object> createPurchase(PurchaseDto request) {
        try {
            Purchase entity = new Purchase();

            this.setValueDtos(entity, request);

            purchaseRepo.save(entity);

            return new ResponseEntity<>(entity, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public  ResponseEntity<Object> updatePurchase(PurchaseDto request, String id) {
        try {
            Optional<Purchase> isExits = purchaseRepo.findById(id);

            if(isExits.isPresent()) {
                Purchase entity = isExits.get();

                this.setValueDtos(entity, request);

                purchaseRepo.save(entity);
                return new ResponseEntity<>(entity, HttpStatus.OK);
            }
            return new ResponseEntity<>("Có lỗi xảy ra", HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Object> getById(String id) {
        try {
            Optional<Purchase> isExits = purchaseRepo.findById(id);
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
        Optional<Purchase> isExits = purchaseRepo.findById(id);

        if (isExits.isPresent()) {
            purchaseRepo.deleteById(id);

            return new ResponseEntity<>("Xóa thành công", HttpStatus.OK);
        }
        throw new RuntimeException("Không tìm thấy");
    }
}
