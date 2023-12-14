package com.humax.parking.exception;

public class DuplicateException extends RuntimeException{
    public DuplicateException() {
        super("존재하지 않는 정보입니다.");
    }

    public DuplicateException(String message) {
        super(message);
    }
}
