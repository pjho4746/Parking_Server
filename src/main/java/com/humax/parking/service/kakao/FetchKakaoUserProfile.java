package com.humax.parking.service.kakao;

import com.humax.parking.dto.SocialUserProfileDto;

public interface FetchKakaoUserProfile {

    SocialUserProfileDto doFetch(String accessToken);
}