package com.humax.parking.service.kakao;

public interface ExchangeKakaoAccessToken {

    String doExchange(String authorizationCode);
}
