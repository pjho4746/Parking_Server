package com.humax.parking.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    /* Common */
    // Basic - C0**
    RUNTIME_EXCEPTION(BAD_REQUEST, "C001", "RUNTIME_EXCEPTION"),
    BAD_DATE_REQUEST(BAD_REQUEST, "C002", "BAD_DATE_REQUEST"),
    OUT_OF_INDEX(BAD_REQUEST,"C003","OUT_OF_INDEX"),

    // User - U0**
    USER_NOT_FOUND(NOT_FOUND, "U004", "해당 사용자를 찾을 수 없습니다. "),

    // Exception
    // File Exception - E0**
    S3_UPLOAD_FAILURE(INTERNAL_SERVER_ERROR, "E001", "NETWORK_FAILURE"),
    FILE_UPLOAD_FAILURE(BAD_REQUEST, "E002", "WRONG_FILE_TYPE"),
    // TOKEN - E1**
    TOKEN_VALIDATE_FAILURE(BAD_REQUEST, "E101", "INVALID_TOKEN"),
    REFRESHTOKEN_EXPIRED(BAD_REQUEST, "E102", "REFRESHTOKEN_EXPIRED"),
    // SSE - E2**
    SSE_CONNECTION_FAILURE(INTERNAL_SERVER_ERROR, "E201", "SSE_CONNECTION_FAILURE"),

    //SUCCESS
    LOGOUT_SUCCESS(OK, "S001", "LOGOUT_SUCCESS");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
