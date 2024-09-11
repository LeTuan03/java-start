package com.example.start_spring.exception;

public enum ErrorCode {
    EXITS_EMAIL_USERNAME(401,"Email hoặc tên đăng nhập hông hợp lệ"),
    SERVER_ERROR(505,"Lỗi hệ thống"),
    INVALID_ACCOUNT(402, "Tên tài khoản hoặc mật khẩu không đúng"),
    OLD_PASS_NOT_MATCH(503, "Mật khẩu không khớp"),
    NOT_FOUND(504, "Không tìm thấy"),
    EXITS_COMIC_FAV(505, "Truyện đã được thêm vào danh sách ưa thich")
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
