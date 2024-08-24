package com.example.start_spring.services.Impl;

import com.example.start_spring.entity.Genres;
import com.example.start_spring.repository.GenresRepo;
import com.example.start_spring.services.GenresService;
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
public class GenresServiceImpl implements GenresService {

    GenresRepo genresRepo;
    @Override
    public List<Genres> getAll() {
        return genresRepo.findAll();
    }
    @Override
    public ResponseEntity<Object>  create(Genres genres) {
        try {
            genresRepo.save(genres);
            return new ResponseEntity<>(genres, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @Override
    public ResponseEntity<Object> update(Genres genres) {
        try {
            Optional<Genres> isExits = genresRepo.findById(genres.getId());
            if (isExits.isPresent()) {
                Genres updateGenres = isExits.get();
                updateGenres.setName(genres.getName());
                genresRepo.save(updateGenres);
                return new ResponseEntity<>(updateGenres, HttpStatus.OK);
            }
            return new ResponseEntity<>("Có lỗi xảy ra", HttpStatus.BAD_REQUEST);
        } catch ( Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Object> getById(String id) {
        try {
            Optional<Genres> isExits = genresRepo.findById(id);
            if (isExits.isPresent()) {
                return new ResponseEntity<>(isExits, HttpStatus.OK);
            }
            return new ResponseEntity<>("Có lỗi xảy ra", HttpStatus.BAD_REQUEST);
        } catch ( Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public String delete(String id) {
        Optional<Genres> isExits = genresRepo.findById(id);

        if (isExits.isPresent()) {
            genresRepo.deleteById(id);
            return "Xóa thành công";
        }
        throw new RuntimeException("Không tìm thấy");
    }

}
