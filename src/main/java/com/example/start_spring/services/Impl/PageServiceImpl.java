package com.example.start_spring.services.Impl;

import com.example.start_spring.DTO.PageDto;
import com.example.start_spring.entity.Page;
import com.example.start_spring.repository.PageRepo;
import com.example.start_spring.services.PageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PageServiceImpl implements PageService {
    PageRepo pageRepo;

    CloudinaryServiceImpl cloudinaryService;

    @Override
    public List<Page> getAll() {
        return pageRepo.findAll();
    }

    @Override
    public ResponseEntity<Object> create(PageDto request) {
        try {
            Page page = this.getPageById(request);
            this.setValuePage(page, request);
            pageRepo.save(page);
            return new ResponseEntity<>(page, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private void setValuePage(Page entity, PageDto request) {
        entity.setPageNumber(request.getPageNumber());
        entity.setImageUrl(request.getImageUrl());
        if(!Objects.isNull(request.getChapterId())) {
            entity.setChapterId(request.getChapterId());
        }
    }

    Page getPageById(PageDto req) throws Exception {
        if (Objects.isNull(req.getId())) {
            return new Page();
        }

        Page page = pageRepo.findById(req.getId()).orElse(null);

        if (Objects.isNull(page)) {
            throw new Exception("not fount!");
        }

        return page;
    }

    @Override
    public ResponseEntity<Object> update(PageDto request, String id) {
        try {
            Optional<Page> isExits = pageRepo.findById(id);
            if (isExits.isPresent()) {
                Page entity = isExits.get();

                this.setValuePage(entity, request);

                pageRepo.save(entity);

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
            Optional<Page> isExits = pageRepo.findById(id);
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
        Optional<Page> isExits = pageRepo.findById(id);

        if (isExits.isPresent()) {
            pageRepo.deleteById(id);
            return new ResponseEntity<>("Xóa thành công", HttpStatus.OK);
        }
        throw new RuntimeException("Không tìm thấy");
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
            page.setCreatedAt(pageDto.getCreatedAt());
            page.setChapterId(chapterId);
            pages.add(page);
        }

        return pages;
    }

    @Override
    public ResponseEntity<Object> updateMulti(List<PageDto> request, String chapterId) {
        try {
            List<Page> pages = this.handlePageDto(request, chapterId);
            pageRepo.saveAll(pages);
            return new ResponseEntity<>(pages, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Object> updateImage(String id, MultipartFile imageUrl) throws IOException {
        Optional<Page> isExits = pageRepo.findById(id);

        if(isExits.isPresent()) {
            Page entity = isExits.get();
            entity.setImageUrl(cloudinaryService.uploadImage(imageUrl));

            pageRepo.save(entity);

            return new ResponseEntity<>(entity, HttpStatus.OK);
        }

        return new ResponseEntity<>("Not found", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Object> createImage(MultipartFile imageUrl) throws IOException {
        if(imageUrl != null) {
            return new ResponseEntity<>(cloudinaryService.uploadImage(imageUrl), HttpStatus.OK);
        }
        return new ResponseEntity<>("Server error", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Object> createImageMulti(List<MultipartFile> imageUrl) throws IOException {
        return null;
    }
}
