package com.humax.parking.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;

    @Column(nullable = false)
    private String nickname;

    @Column(name = "profile_image_filename")
    private String profileImageFilename;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

//    @Column(unique = true, nullable = false)
//    private String accountId;

    @Column(nullable = false)
    private String email;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "login_type", nullable = false)
//    private LoginType loginType;

    @Builder
    public User(String nickname, String profileImageUrl, String email) {
        //this.userId = TsidCreator.getTsid().toLong();
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        //this.accountId = TsidCreator.getTsid().toString(); // 최초 랜덤값
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

//    public void updateProfile(String accountId, String nickname, String profileImageFileName,
//                              String profileImageUrl) {
//
//        this.accountId = accountId;
//        this.nickname = nickname;
//        this.profileImageFilename = profileImageFileName;
//        this.profileImageUrl = profileImageUrl;
//    }
}