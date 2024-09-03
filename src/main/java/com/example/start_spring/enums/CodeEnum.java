package com.example.start_spring.enums;

public enum CodeEnum {
    REGISTER_SUCCESS(200, "Đăng ký thành công tài khoản"),
    RESPONSE_OK(200, "Thành công"),
    ;

    CodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
