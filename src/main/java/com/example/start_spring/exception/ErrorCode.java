package com.example.start_spring.exception;

public enum ErrorCode {
    EXITS_EMAIL_USERNAME(401,"Email hoặc tên đăng nhập hông hợp lệ"),
    SERVER_ERROR(505,"Lỗi hệ thống"),
    INVALID_ACCOUNT(402, "Tên tài khoản hoặc mật khẩu không đúng")
    ;
    ErrorCode(int code, String message) {
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