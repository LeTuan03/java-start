package com.example.start_spring.services.Impl;

import com.example.start_spring.DTO.ChapterRequest;
import com.example.start_spring.DTO.PageDto;
import com.example.start_spring.entity.Chapter;
import com.example.start_spring.entity.Comic;
import com.example.start_spring.entity.Page;
import com.example.start_spring.repository.ChapterRepo;
import com.example.start_spring.repository.ComicRepo;
import com.example.start_spring.services.ChapterService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChapterServiceImpl implements ChapterService {
    ChapterRepo chapterRepo;
    ComicRepo comicRepo;

    @Override
    public List<Chapter> getAll() {
        return chapterRepo.findAll();
    }

    @Override
    public ResponseEntity<Object> create(ChapterRequest chapter) {
        try {
            Comic comic = comicRepo.findById(chapter.getComicId())
                    .orElseThrow(() -> new RuntimeException("Comic not found"));

            Chapter entity = this.getChapterById(chapter);
            this.setValueChapter(entity, chapter);

            chapterRepo.save(entity);
            return new ResponseEntity<>(entity, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private void setValueChapter(Chapter entity, ChapterRequest request){
        entity.setTitle(request.getTitle());
        entity.setChapterNumber(request.getChapterNumber());
        entity.setComicId(request.getComicId());
    }

    Chapter getChapterById(ChapterRequest req) throws Exception {
        if (Objects.isNull(req.getId())){
            return new Chapter();
        }

        Chapter chapter = chapterRepo.findById(req.getId()).orElse(null);

        if (Objects.isNull(chapter)){
            throw new Exception("not found!");
        }

        return chapter;
    }

    private List<Page> handlePageDtos(Chapter chapter, List<PageDto> pageDtos) {
        List<Page> pages = new ArrayList<>();
        for (PageDto pageDto : pageDtos) {
            Page page = new Page();
            page.setId(pageDto.getId());
            page.setPageNumber(pageDto.getPageNumber());
            page.setImageUrl(pageDto.getImageUrl());
            page.setChapterId(chapter.getId());
            pages.add(page);
        }
        return pages;
    }

    @Override
    public ResponseEntity<Object> update(ChapterRequest chapter) {
        try {
            Optional<Chapter> isExits = chapterRepo.findById(chapter.getId());
            if(isExits.isPresent()){
                Chapter updateChapter = isExits.get();

                this.setValueChapter(updateChapter, chapter);

                List<Page> pages = this.handlePageDtos(updateChapter, chapter.getPages());

                updateChapter.setPages((Set<Page>) pages);

                chapterRepo.save(updateChapter);

                return new ResponseEntity<>(updateChapter, HttpStatus.OK);
            }
            return new ResponseEntity<>("Có lỗi xảy ra", HttpStatus.BAD_REQUEST);
        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Object> getById(String id) {
        try {
            Optional<Chapter> isExits = chapterRepo.findById(id);
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
        Optional<Chapter> isExits = chapterRepo.findById(id);

        if (isExits.isPresent()) {
            chapterRepo.deleteById(id);
            return new ResponseEntity<>("Xóa thành công", HttpStatus.OK);
        }
        throw new RuntimeException("Không tìm thấy");
    }
}
