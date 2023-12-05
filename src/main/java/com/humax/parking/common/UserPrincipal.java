package com.humax.parking.common;

import com.humax.parking.model.User;
import lombok.Getter;

import java.io.Serializable;

//'UserPrincipal'은 인증된 사용자의 주요 정보를 갖고 있는 객체로, 주로 보안 및 인증 관련 기능에서 활용됩니다.
@Getter
public class UserPrincipal implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Long id;
    private final String nickname;
    private final String profileImageUrl;
    //private final String accountId;
    private final String email;
    //private final LoginType loginType;

    public UserPrincipal(Long id, String nickname, String profileImageUrl, String email) {
        this.id = id;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        //this.accountId = accountId;
        this.email = email;
        //this.loginType = loginType;
    }

    public static UserPrincipal builder(User user) {

        return new UserPrincipal(
                user.getUserId(),
                user.getNickname(),
                user.getProfileImageUrl(),
                //user.getAccountId(),
                user.getEmail());
                //user.getLoginType());
    }


}
