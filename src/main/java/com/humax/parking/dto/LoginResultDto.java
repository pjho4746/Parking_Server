package com.humax.parking.dto;

import lombok.Getter;

@Getter
public class LoginResultDto {

    private final String token;
    private final boolean isNewUser;

    public LoginResultDto(String token, boolean isNewUser) {
        this.token = token;
        this.isNewUser = isNewUser;
    }
}
