package com.example.start_spring.services.Impl;

import com.example.start_spring.DTO.ChapterRequest;
import com.example.start_spring.DTO.PageDto;
import com.example.start_spring.entity.Chapter;
import com.example.start_spring.entity.Comic;
import com.example.start_spring.entity.Page;
import com.example.start_spring.repository.ChapterRepo;
import com.example.start_spring.repository.ComicRepo;
import com.example.start_spring.repository.PageRepo;
import com.example.start_spring.services.ChapterService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChapterServiceImpl implements ChapterService {
    ChapterRepo chapterRepo;
    ComicRepo comicRepo;

    PageRepo pageRepo;

    @Override
    public List<Chapter> getAll() {
        return chapterRepo.findAll();
    }

    @Override
    public ResponseEntity<Object> create(ChapterRequest chapter) {
        try {
            Comic comic = comicRepo.findById(chapter.getComicId())
                    .orElseThrow(() -> new RuntimeException("Comic not found"));

            chapter.setCreatedAt(LocalDateTime.now());

            Chapter entity = this.getChapterById(chapter);

            this.setValueChapter(entity, chapter);

            chapterRepo.save(entity);

            if (chapter.getPages() != null && !chapter.getPages().isEmpty()) {
                List<Page> pageUpdate = this.updateMulti(chapter.getPages(), entity.getId());
                if (pageUpdate != null && !pageUpdate.isEmpty()) {
                    entity.setPages(new HashSet<>(pageUpdate));
                }
            }

            chapterRepo.save(entity);

            return new ResponseEntity<>(entity, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    private List<Page> updateMulti(List<PageDto> request, String chapterId) {
        try {
            List<Page> pages = this.handlePageDto(request, chapterId);
            pageRepo.saveAll(pages);
            return pages;
        } catch (Exception ex) {
            return null;
        }
    }

    private List<Page> handlePageDto(List<PageDto> pageDtos, String chapterId) {
        List<Page> pages = new ArrayList<>();

        for (PageDto pageDto : pageDtos) {
            Page page = new Page();
            if (pageDto.getId() != null && !pageDto.getId().isEmpty()) {
                page.setId(pageDto.getId());
            }
            page.setPageNumber(pageDto.getPageNumber());
            page.setImageUrl(pageDto.getImageUrl());
            page.setChapterId(chapterId);
            pages.add(page);
        }

        return pages;
    }

    private void setValueChapter(Chapter entity, ChapterRequest request) {
        entity.setTitle(request.getTitle());
        entity.setChapterNumber(request.getChapterNumber());
        entity.setComicId(request.getComicId());

//        Date date = new Date();
//        LocalDateTime localDateTime = date.toInstant()
//                .atZone(ZoneId.systemDefault())
//                .toLocalDateTime();
//        entity.setCreatedAt(localDateTime);
    }

    Chapter getChapterById(ChapterRequest req) throws Exception {
        if (Objects.isNull(req.getId())) {
            return new Chapter();
        }

        Chapter chapter = chapterRepo.findById(req.getId()).orElse(null);

        if (Objects.isNull(chapter)) {
            throw new Exception("not found!");
        }

        return chapter;
    }

    private List<Page> handlePageDtos(Chapter chapter, List<PageDto> pageDtos) {
        List<Page> pages = new ArrayList<>();

        for (PageDto pageDto : pageDtos) {
            Page page;

            // Kiểm tra xem trang có tồn tại trong database hay không dựa vào ID
            if (pageDto.getId() != null) {
                Optional<Page> existingPage = pageRepo.findById(pageDto.getId());
                if (existingPage.isPresent()) {
                    page = existingPage.get();
                    // Cập nhật các thuộc tính
                    page.setPageNumber(pageDto.getPageNumber());
                    page.setImageUrl(pageDto.getImageUrl());
                    page.setChapterId(chapter.getId());
                } else {
                    // Nếu ID không tồn tại, tạo mới
                    page = new Page();
                    page.setId(pageDto.getId());
                    page.setPageNumber(pageDto.getPageNumber());
                    page.setImageUrl(pageDto.getImageUrl());
                    page.setChapterId(chapter.getId());
                    page.setCreatedAt(pageDto.getCreatedAt());
                }
            } else {
                // Nếu ID là null, tạo mới
                page = new Page();
                page.setPageNumber(pageDto.getPageNumber());
                page.setImageUrl(pageDto.getImageUrl());
                page.setChapterId(chapter.getId());
                page.setCreatedAt(chapter.getCreatedAt());
            }

            // Thêm page vào danh sách
            pages.add(page);
        }

        return pages;
    }

    @Override
    public ResponseEntity<Object> update(ChapterRequest chapter) {
        try {
            Optional<Chapter> isExits = chapterRepo.findById(chapter.getId());
            if (isExits.isPresent()) {
                Chapter updateChapter = isExits.get();

                this.setValueChapter(updateChapter, chapter);

                List<Page> pages = this.handlePageDtos(updateChapter, chapter.getPages());

                pageRepo.saveAll(pages);

                // Xóa các phần tử cũ
                updateChapter.getPages().clear();

                // Thêm các trang mới
                updateChapter.getPages().addAll(pages);


                chapterRepo.save(updateChapter);

                return new ResponseEntity<>(updateChapter, HttpStatus.OK);
            }
            return new ResponseEntity<>("Có lỗi xảy ra", HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
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
        } catch (Exception ex) {
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
