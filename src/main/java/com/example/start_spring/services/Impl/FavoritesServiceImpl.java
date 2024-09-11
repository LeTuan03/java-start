package com.example.start_spring.services.Impl;

import com.example.start_spring.DTO.ApiResponse;
import com.example.start_spring.DTO.FavoriteDto;
import com.example.start_spring.entity.Account;
import com.example.start_spring.entity.Comic;
import com.example.start_spring.entity.Favorites;
import com.example.start_spring.enums.CodeEnum;
import com.example.start_spring.exception.AppException;
import com.example.start_spring.exception.ErrorCode;
import com.example.start_spring.repository.AccountRepo;
import com.example.start_spring.repository.ComicRepo;
import com.example.start_spring.repository.FavoritesRepo;
import com.example.start_spring.services.FavoritesService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FavoritesServiceImpl implements FavoritesService {

    FavoritesRepo favoritesRepo;
    AccountRepo accountRepo;
    ComicRepo comicRepo;

    private boolean validateCreate(String userId, String comicId) {
        return favoritesRepo.existsByUserIdAndComicId(userId, comicId);
    }

    @Override
    public ApiResponse<Favorites> create(String userId, String comicId) {

        if(this.validateCreate(userId, comicId)) {
            throw new AppException(ErrorCode.EXITS_COMIC_FAV);
        }

        ApiResponse<Favorites> apiResponse = new ApiResponse();
        Favorites entity = new Favorites();
        Optional<Account> account = accountRepo.findById(userId);
        Optional<Comic> comic = comicRepo.findById(comicId);

        if(!account.isPresent() || !comic.isPresent()) {
            throw new AppException(ErrorCode.NOT_FOUND);
        }

        entity.setUser(account.get());
        entity.setComic(comic.get());
        apiResponse.setCode(CodeEnum.RESPONSE_OK.getCode());
        apiResponse.setMessage(CodeEnum.RESPONSE_OK.getMessage());
        apiResponse.setData(favoritesRepo.save(entity));

        return apiResponse;
    }

    @Override
    public ApiResponse<String> delete(String id) {
        ApiResponse<String> apiResponse = new ApiResponse();

        favoritesRepo.deleteById(id);

        apiResponse.setCode(CodeEnum.RESPONSE_OK.getCode());
        apiResponse.setMessage(CodeEnum.RESPONSE_OK.getMessage());
        return apiResponse;
    }

    @Override
    public ApiResponse<List<FavoriteDto>> getByUser(String userId) {
        List<FavoriteDto> favoriteDtos = favoritesRepo.findByUserId(userId);
        ApiResponse<List<FavoriteDto>> apiResponse = new ApiResponse();
        apiResponse.setData(favoriteDtos);
        return apiResponse;
    }
}
