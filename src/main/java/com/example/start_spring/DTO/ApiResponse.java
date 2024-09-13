package com.example.start_spring.DTO;

import com.example.start_spring.enums.CodeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    int code = 1000;
    String message = CodeEnum.RESPONSE_OK.getMessage();
    T data;

    public ApiResponse(ApiResponse response) {
        this.code = response.getCode();
        this.message = response.getMessage();
        this.data = (T) response.getData();
    }
    public ApiResponse(T data) {
        this.data = data;
    }
}
