package com.example.start_spring.services.Impl;

import com.example.start_spring.DTO.*;
import com.example.start_spring.entity.Author;
import com.example.start_spring.entity.Comic;
import com.example.start_spring.entity.Genres;
import com.example.start_spring.repository.*;
import com.example.start_spring.services.ComicService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ComicServiceImpl implements ComicService {

    ComicRepo comicRepo;
    GenresRepo genresRepo;
    AuthorRepo authorRepo;
    FavoritesRepo favoritesRepo;
    RateComicRepo rateComicRepo;

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
        entity.setStatus(request.getStatus());
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setNewestChapterNumber(request.getNewestChapterNumber());
        if (!CollectionUtils.isEmpty(request.getGenres())) {
            Set<Genres> genresSet = new HashSet<>();

            for (Genres genre : request.getGenres()) {
                Optional<Genres> existingGenre = genresRepo.findById(genre.getId());

                if (existingGenre.isPresent()) {
                    genresSet.add(existingGenre.get());
                } else {
                    genresSet.add(genre);
                }
            }

            entity.setGenres(genresSet);
        } else {
            entity.setGenres(null);
        }
//        if (request.getChapters() != null && !request.getChapters().isEmpty()) {
//            entity.setChapters(request.getChapters());
//        }
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

    private Double getAverageRate(String comicId) {
        List<RateComicDto> allRate = rateComicRepo.findAllByComicId(comicId);
        int totalRate = allRate.stream().mapToInt(RateComicDto::getStar).sum();
        return totalRate / 5.0;
    }

    @Override
    public ResponseEntity<Object> getById(String id) {
        try {

            Optional<Comic> comic = comicRepo.findById(id);
            if (Objects.isNull(comic)) {
                return new ResponseEntity<>("Comic không tồn tại", HttpStatus.BAD_REQUEST);
            }
            Comic entity = comic.get();

            entity.setViewCount(Objects.isNull(entity.getViewCount()) ? 0 : entity.getViewCount() + 1);

            comicRepo.save(entity);

            Integer numFollow = favoritesRepo.countByComicId(id);


            GetComicDto dto = new GetComicDto();
            dto.setId(comic.get().getId());
            dto.setTitle(comic.get().getTitle());
            dto.setDescription(comic.get().getDescription());
            dto.setAuthor(comic.get().getAuthor());
            dto.setCoverImage(comic.get().getCoverImage());
            dto.setGenres(comic.get().getGenres());
            dto.setChapters(comic.get().getChapters());
            dto.setLikeCount(comic.get().getLikeCount());
            dto.setViewCount(comic.get().getViewCount());
            dto.setCreatedAt(comic.get().getCreatedAt());
            dto.setUpdatedAt(comic.get().getUpdatedAt());
            dto.setStatus(comic.get().getStatus());
            dto.setNewestChapterNumber(comic.get().getNewestChapterNumber());
            dto.setNumFollow(numFollow);
            dto.setAverageRate(this.getAverageRate(comic.get().getId()));

            return new ResponseEntity<>(dto, HttpStatus.OK);
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

    @Override
    public List<ComicListByUser> getAllByUser() {
        return comicRepo.findAll().stream()
                .map(comic -> new ComicListByUser(comic.getId(), comic.getTitle(), comic.getCoverImage(), comic.getUpdatedAt(), comic.getAuthor().getName(), comic.getNewestChapterNumber()))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<Object> like(String id) {
        Optional<Comic> isExits = comicRepo.findById(id);
        if (Objects.isNull(isExits)) {
            return new ResponseEntity<>("Không tìm thấy", HttpStatus.NOT_FOUND);
        }
        Comic entity = isExits.get();

        entity.setLikeCount(Objects.isNull(entity.getLikeCount()) ? 1 : entity.getLikeCount() + 1);
        comicRepo.save(entity);

        return new ResponseEntity<>("Thành công", HttpStatus.OK);
    }

    @Override
    public ApiResponse<List<ComicListByUser>> getListComicOrderByUpdatedAt() {
        ApiResponse<List<ComicListByUser>> apiResponse = new ApiResponse<>(comicRepo.getListComicOrderByUpdatedAt());
        return apiResponse;
    }
}
