package com.example.start_spring.services.Impl;

import com.example.start_spring.DTO.AuthorDto;
import com.example.start_spring.entity.Author;
import com.example.start_spring.repository.AuthorRepo;
import com.example.start_spring.services.AuthorService;
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
public class AuthorServiceImpl implements AuthorService {
    AuthorRepo authorRepo;

    @Override
    public List<Author> getAll() {
        return authorRepo.findAll();
    }

    @Override
    public ResponseEntity<Object> create(AuthorDto request) {
        try {
            Author entity = new Author();
            this.setValueAuthorDto(entity, request);

            authorRepo.save(entity);

            return new ResponseEntity<>(entity, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    void setValueAuthorDto(Author entity, AuthorDto request) {
        entity.setPhone(request.getPhone());
        entity.setAddress(request.getAddress());
        entity.setType(request.getType());
        entity.setStatus(request.getStatus());
        entity.setDateOfIssue(request.getDateOfIssue());
        entity.setEmail(request.getEmail());
        entity.setName(request.getName());
    }

    @Override
    public ResponseEntity<Object> update(AuthorDto request, String id) {
        try {
            Optional<Author> isExits = authorRepo.findById(id);
            if(isExits.isPresent()) {
                Author entity = isExits.get();
                this.setValueAuthorDto(entity, request);

                authorRepo.save(entity);

                return new ResponseEntity<>(entity, HttpStatus.OK);
            }
            return new ResponseEntity<>("Có lỗi xảy ra", HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Object> getById(String id) {
        try {
            Optional<Author> isExits = authorRepo.findById(id);
            if (isExits.isPresent()) {
                return new ResponseEntity<>(isExits, HttpStatus.OK);
            }
            return new ResponseEntity<>("Có lỗi xảy ra", HttpStatus.BAD_REQUEST);
        } catch ( Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Object> delete(String id) {
        Optional<Author> isExits = authorRepo.findById(id);

        if (isExits.isPresent()) {
            authorRepo.deleteById(id);
            return new ResponseEntity<>("Xóa thành công", HttpStatus.OK);
        }
        throw new RuntimeException("Không tìm thấy");
    }
}
