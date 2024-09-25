package com.example.start_spring.services.Impl;

import com.example.start_spring.DTO.*;
import com.example.start_spring.entity.Account;
import com.example.start_spring.entity.Author;
import com.example.start_spring.enums.CodeEnum;
import com.example.start_spring.exception.AppException;
import com.example.start_spring.exception.ErrorCode;
import com.example.start_spring.repository.AccountRepo;
import com.example.start_spring.repository.AuthorRepo;
import com.example.start_spring.services.AccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountServiceImpl implements AccountService {

    AccountRepo accountRepo;

    AuthorRepo authorRepo;

    @Override
    public List<AccountResponseDto> getAll() {
        return accountRepo.findAll().stream()
                .map(AccountResponseDto::new)
                .collect(Collectors.toList());
    }

    void setValueAccount(Account entity, AccountRequestDto accountRequestDto, boolean isUpdate) {
        entity.setAvatar(accountRequestDto.getAvatar());
        entity.setUsername(accountRequestDto.getUsername());
        entity.setEmail(accountRequestDto.getEmail());
        entity.setPhone(accountRequestDto.getPhone());
        entity.setRole(accountRequestDto.getRole());
        entity.setIsActive(accountRequestDto.getIsActive());
        entity.setPassword(isUpdate ? entity.getPassword() : accountRequestDto.getPassword());
        entity.setFullName(accountRequestDto.getFullName());
        entity.setIsAllowRegister(accountRequestDto.getIsAllowRegister());
        entity.setAddress(accountRequestDto.getAddress());
        entity.setCoverImage(accountRequestDto.getCoverImage());
        if (!Objects.isNull(accountRequestDto.getAuthor())) {
            entity.setAuthor(accountRequestDto.getAuthor());
        } else {
            entity.setAuthor(null);
        }

    }

    boolean checkExistAuthor(Author author) {
        return accountRepo.existsByAuthor(author);
    }

    @Override
    public ResponseEntity<Object> create(AccountRequestDto request) {
        try {
            boolean validateEmailAndUsername = accountRepo.existsByEmailOrUsername(request.getEmail(), request.getUsername());


            if (validateEmailAndUsername) {
                throw new AppException(ErrorCode.EXITS_EMAIL_USERNAME);
            }
            if (Objects.nonNull(request.getAuthor()) && checkExistAuthor(request.getAuthor())) {
                return new ResponseEntity<>("Tác giả không hợp lệ", HttpStatus.BAD_REQUEST);
            }
            Account entity = new Account();
            this.setValueAccount(entity, request, false);
            entity.setIsAllowRegister(true);
            accountRepo.save(entity);
            return new ResponseEntity<>(entity, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Object> update(String id, AccountRequestDto request) {
        try {
            Optional<Account> isExits = accountRepo.findById(id);
            boolean isExistEmail = accountRepo.existsByEmail(request.getEmail());

            boolean isExitsEmailUpdate = isExistEmail && !isExits.get().getEmail().equals(request.getEmail());
            boolean isExitsAuthorUpdate = checkExistAuthor(request.getAuthor())
                    && !Objects.equals(isExits.get().getAuthor(), request.getAuthor());

            if (isExitsEmailUpdate) {
                return new ResponseEntity<>("Email đã tồn tại", HttpStatus.BAD_REQUEST);
            }

            if (isExitsAuthorUpdate) {
                return new ResponseEntity<>("Tác giả không hợp lệ", HttpStatus.BAD_REQUEST);
            }
            if (isExits.isPresent()) {
                Account entity = isExits.get();

                if (Boolean.valueOf(entity.getIsAllowRegister())) {
                    request.setIsAllowRegister(true);
                } else {
                    request.setIsAllowRegister(false);
                }

                this.setValueAccount(entity, request, true);


                accountRepo.save(entity);

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
            Optional<Account> isExits = accountRepo.findById(id);
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
        Optional<Account> isExits = accountRepo.findById(id);

        if (isExits.isPresent()) {
            accountRepo.deleteById(id);
            return new ResponseEntity<>("Xóa thành công", HttpStatus.OK);
        }
        throw new RuntimeException("Không tìm thấy");
    }

    void setValuesAuthor(Author entity, AuthorDto request) {
        entity.setType(request.getType());
        entity.setStatus(request.getStatus());
        entity.setAddress(request.getAddress());
        entity.setName(request.getName());
        entity.setEmail(request.getEmail());
//        entity.setDateOfIssue(request.getDateOfIssue());
        entity.setPhone(request.getPhone());
    }

    @Override
    public ResponseEntity<Object> registerAuthor(String accountId, AuthorDto request) {
        try {
            Optional<Account> isExits = accountRepo.findById(accountId);

            if (isExits.isPresent()) {
                Account entity = isExits.get();
                if (Boolean.FALSE.equals(entity.getIsAllowRegister())) {
                    return new ResponseEntity<>("Bạn không có quyền", HttpStatus.BAD_REQUEST);
                }
                Author author = new Author();

                this.setValuesAuthor(author, request);

                entity.setAuthor(authorRepo.save(author));

                entity.setIsAllowRegister(false);

                accountRepo.save(entity);

                return new ResponseEntity<>(entity, HttpStatus.OK);
            }
            return new ResponseEntity<>("Có lỗi xảy ra", HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private boolean checkExistByEmailAndUsername(String email, String username) {
        boolean isExistEmail = accountRepo.existsByEmail(email);
        boolean isExistUsername = accountRepo.existsByUsername(username);

        return isExistEmail || isExistUsername;
    }

    @Override
    public AccountResponseDto register(AccountRequestDto request) {
        boolean validateEmailAndUsername = accountRepo.existsByEmailOrUsername(request.getEmail(), request.getUsername());
        if (validateEmailAndUsername) {
            throw new AppException(ErrorCode.EXITS_EMAIL_USERNAME);
        }
        Account entity = new Account();

        this.setValueAccount(entity, request, false);
        entity.setIsAllowRegister(true);

        return this.convertToResponseDto(accountRepo.save(entity));
    }

    @Override
    public ApiResponse<AccountResponseDto> login(String username, String password) {
        Optional<Account> account = accountRepo.findByUsernameAndPassword(username, password);
        ApiResponse<AccountResponseDto> apiResponse = new ApiResponse();
        if(account.isPresent()) {
            apiResponse.setCode(CodeEnum.RESPONSE_OK.getCode());
            apiResponse.setMessage(CodeEnum.RESPONSE_OK.getMessage());
            AccountResponseDto response = this.convertToResponseDto(account.get());
            apiResponse.setData(response);
            return apiResponse;
        }
        apiResponse.setCode(ErrorCode.INVALID_ACCOUNT.getCode());
        apiResponse.setMessage(ErrorCode.INVALID_ACCOUNT.getMessage());
        return apiResponse;
    }

    @Override
    public ApiResponse<AccountResponseDto> changePassword(ChangePasswordDto request) {
        Optional<Account> accountExist = accountRepo.findByEmail(request.getEmail());

        if(accountExist.isPresent()) {
            Account entity = accountExist.get();
            if(!entity.getPassword().equals(request.getOldPass())) {
                throw new AppException(ErrorCode.OLD_PASS_NOT_MATCH);
            }
            entity.setPassword(request.getNewPass());
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setCode(CodeEnum.RESPONSE_OK.getCode());
            apiResponse.setMessage(CodeEnum.RESPONSE_OK.getMessage());
            apiResponse.setData(accountRepo.save(entity));
            return apiResponse;
        }
        throw new AppException(ErrorCode.NOT_FOUND);
    }

    private AccountResponseDto convertToResponseDto(Account account) {
        AccountResponseDto response = new AccountResponseDto();
        response.setId(account.getId());
        response.setUsername(account.getUsername());
        response.setEmail(account.getEmail());
        response.setRole(account.getRole());
        response.setIsActive(true);
        response.setFullName(account.getFullName());
        response.setIsAllowRegister(account.getIsAllowRegister());
        response.setAddress(account.getAddress());
        response.setCoverImage(account.getCoverImage());
        return response;
    }
}
