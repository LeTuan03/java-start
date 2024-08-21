package com.example.start_spring.services.Impl;

import com.example.start_spring.DTO.ComicDto;
import com.example.start_spring.entity.Author;
import com.example.start_spring.entity.Comic;
import com.example.start_spring.entity.Genres;
import com.example.start_spring.repository.AuthorRepo;
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

import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ComicServiceImpl implements ComicService {

    ComicRepo comicRepo;
    GenresRepo genresRepo;
    AuthorRepo authorRepo;

    @Override
    public List<Comic> getAll() {
        return comicRepo.findAll();
    }

    @Override
    public ResponseEntity<Object> create(ComicDto comic) {
        try {

            Comic entity = new Comic();

            this.setValueDtos(entity, comic);

            if (!CollectionUtils.isEmpty(comic.getGenres())) {
                this.handleGenreDtos(comic);
            }

            comicRepo.save(entity);

            return new ResponseEntity<>(entity, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    void setValueDtos(Comic entity, ComicDto request) {
        Optional<Author> selectAuthor = authorRepo.findById(request.getAuthor().getId());
        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());
        entity.setCoverImage(request.getCoverImage());
        if (!CollectionUtils.isEmpty(request.getGenres())) {
            entity.setGenres(request.getGenres());
        }
        if (request.getChapters() != null && !request.getChapters().isEmpty()) {
            entity.setChapters(request.getChapters());
        }
        if (selectAuthor.isPresent()) {
            entity.setAuthor(selectAuthor.get());
        }
    }

    private void handleGenreDtos(ComicDto comic) {
        Set<Genres> genres = new HashSet<>();

        for (Genres genre : comic.getGenres()) {
            Optional<Genres> existingGenre = genresRepo.findById(genre.getId());
            existingGenre.ifPresent(genres::add);
        }

        comic.setGenres(genres);
    }

    @Override
    public ResponseEntity<Object> update(ComicDto request, String id) {
        try {
            Optional<Comic> isExits = comicRepo.findById(id);
            if (isExits.isPresent()) {
                Comic updateComic = isExits.get();

                this.setValueDtos(updateComic, request);

                if (!CollectionUtils.isEmpty(request.getGenres())) {
                    this.handleGenreDtos(request);
                }

                comicRepo.save(updateComic);

                return new ResponseEntity<>(updateComic, HttpStatus.OK);
            }
            return new ResponseEntity<>("Comic không tồn tại", HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @Override
    public ResponseEntity<Object> getById(String id) {
        try {

            Optional<Comic> comic = comicRepo.findById(id);
            if (Objects.isNull(comic)) {
                return new ResponseEntity<>("Comic không tồn tại", HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(comic, HttpStatus.OK);
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
