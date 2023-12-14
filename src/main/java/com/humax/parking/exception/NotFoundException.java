package com.humax.parking.exception;


public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super("존재하지 않는 정보입니다.");
    }

    public NotFoundException(String message) {
        super(message);
    }

    public ErrorResponse toErrorResponse() {
        return ErrorResponse.builder()
                .message(this.getMessage()) // 예외 객체의 메시지 사용
                .build();
    }
}

