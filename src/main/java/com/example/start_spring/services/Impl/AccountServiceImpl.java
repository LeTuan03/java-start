package com.example.start_spring.services.Impl;

import com.example.start_spring.DTO.AccountRequestDto;
import com.example.start_spring.DTO.AccountResponseDto;
import com.example.start_spring.DTO.AuthorDto;
import com.example.start_spring.entity.Account;
import com.example.start_spring.entity.Author;
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

    void setValueAccount(Account entity, AccountRequestDto accountRequestDto) {
        entity.setAvatar(accountRequestDto.getAvatar());
        entity.setUsername(accountRequestDto.getUsername());
        entity.setEmail(accountRequestDto.getEmail());
        entity.setPhone(accountRequestDto.getPhone());
        entity.setRole(accountRequestDto.getRole());
        entity.setIsActive(accountRequestDto.getIsActive());
        entity.setPassword(accountRequestDto.getPassword());
        entity.setIsAllowRegister(accountRequestDto.getIsAllowRegister());

        if(!Objects.isNull(accountRequestDto.getAuthor())) {
            entity.setAuthor(accountRequestDto.getAuthor());
        }

    }

    @Override
    public ResponseEntity<Object> create(AccountRequestDto request) {
        try {
            Optional<Account> isExitEmail = accountRepo.findByEmail(request.getEmail());
            if (isExitEmail.isPresent()) {
                return new ResponseEntity<>("Email đã tồn tại", HttpStatus.BAD_REQUEST);
            }
            Account entity = new Account();
            this.setValueAccount(entity, request);
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

            if (isExits.isPresent()) {
                Account entity = isExits.get();

                if(Boolean.valueOf(entity.getIsAllowRegister())) {
                    request.setIsAllowRegister(true);
                } else {
                    request.setIsAllowRegister(false);
                }

                this.setValueAccount(entity, request);


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
        entity.setDateOfIssue(request.getDateOfIssue());
        entity.setPhone(request.getPhone());
    }
    @Override
    public ResponseEntity<Object> registerAuthor(String accountId, AuthorDto request) {
        try {
            Optional<Account> isExits = accountRepo.findById(accountId);

            if (isExits.isPresent()) {
                Account entity = isExits.get();
                if(Boolean.FALSE.equals(entity.getIsAllowRegister())) {
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


}
