package com.example.start_spring.services.Impl;

import com.example.start_spring.DTO.ApiResponse;
import com.example.start_spring.DTO.RateComicDto;
import com.example.start_spring.entity.Account;
import com.example.start_spring.entity.Comic;
import com.example.start_spring.entity.RateComic;
import com.example.start_spring.enums.CodeEnum;
import com.example.start_spring.exception.AppException;
import com.example.start_spring.exception.ErrorCode;
import com.example.start_spring.repository.AccountRepo;
import com.example.start_spring.repository.ComicRepo;
import com.example.start_spring.repository.RateComicRepo;
import com.example.start_spring.services.RateComicService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RateComicServiceImpl implements RateComicService {
    RateComicRepo rateComicRepo;
    AccountRepo accountRepo;
    ComicRepo comicRepo;

    private boolean validateCreate(String userId, String comicId) {
        return rateComicRepo.existsByUserIdAndComicId(userId, comicId);
    }

    @Override
    public ApiResponse<RateComic> create(String userId, String comicId, Integer star) {

        if(this.validateCreate(userId, comicId)) {
            throw new AppException(ErrorCode.EXITS_COMIC_FAV);
        }

        ApiResponse<RateComic> apiResponse = new ApiResponse();
        RateComic entity = new RateComic();
        Optional<Account> account = accountRepo.findById(userId);
        Optional<Comic> comic = comicRepo.findById(comicId);

        if(!account.isPresent() || !comic.isPresent() || Objects.isNull(star)) {
            throw new AppException(ErrorCode.NOT_FOUND);
        }

        entity.setUser(account.get());
        entity.setComic(comic.get());
        entity.setStar(star);

        apiResponse.setCode(CodeEnum.RESPONSE_OK.getCode());
        apiResponse.setMessage(CodeEnum.RESPONSE_OK.getMessage());
        apiResponse.setData(rateComicRepo.save(entity));

        return apiResponse;
    }

    @Override
    public ApiResponse<Object> getAll() {
        List<RateComicDto> data = rateComicRepo.findAllReq();
        ApiResponse<Object> apiResponse = new ApiResponse<>(data);
        return apiResponse;
    }
}
