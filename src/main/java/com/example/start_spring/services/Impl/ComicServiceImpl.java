package com.example.start_spring.services.Impl;

import com.example.start_spring.entity.Comic;
import com.example.start_spring.entity.Genres;
import com.example.start_spring.repository.ComicRepo;
import com.example.start_spring.repository.GenresRepo;
import com.example.start_spring.services.ComicService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ComicServiceImpl implements ComicService {

    ComicRepo comicRepo;
    GenresRepo genresRepo;

    @Override
    public List<Comic> getAll() {
        return comicRepo.findAll();
    }

    @Override
    public ResponseEntity<Object> create(Comic comic) {
        try {
            if (!CollectionUtils.isEmpty(comic.getGenres())) {
                this.handleGenreDtos(comic);
            }

            Comic comic1 = comicRepo.save(comic);
            return new ResponseEntity<>(comic1, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private void handleGenreDtos(Comic comic) {
        Set<Genres> genres = new HashSet<>();

        for (Genres genre : comic.getGenres()) {
            Optional<Genres> existingGenre = genresRepo.findById(genre.getId());
            existingGenre.ifPresent(genres::add);
        }

        comic.setGenres(genres);
    }

    @Override
    public ResponseEntity<Object> update(Comic comic) {
        try {
            Optional<Comic> isExits = comicRepo.findById(comic.getId());
            if (isExits.isPresent()) {
                Comic updateComic = isExits.get();

                updateComic.setTitle(comic.getTitle());
                updateComic.setDescription(comic.getDescription());
                updateComic.setCoverImage(comic.getCoverImage());
                updateComic.setAuthor(comic.getAuthor());

                if (!CollectionUtils.isEmpty(comic.getGenres())) {
                    this.handleGenreDtos(comic);
                }

                comicRepo.save(updateComic);

                return new ResponseEntity<>(updateComic, HttpStatus.OK);
            }
            return new ResponseEntity<>("Có lỗi xảy ra", HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Object> getById(String id) {
        try {
            Optional<Comic> isExits = comicRepo.findById(id);
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
        Optional<Comic> isExits = comicRepo.findById(id);

        if (isExits.isPresent()) {
            comicRepo.deleteById(id);
            return new ResponseEntity<>("Xóa thành công", HttpStatus.OK);
        }
        throw new RuntimeException("Không tìm thấy");
    }
}
