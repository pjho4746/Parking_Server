package com.humax.parking.controller;


import com.humax.parking.dto.LoginResultDto;
import com.humax.parking.dto.ParkingInfoDTO;
import com.humax.parking.dto.TokenDTO;
import com.humax.parking.service.UserService;
import com.humax.parking.service.kakao.KakaoLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.ui.Model;

@RestController
@RequiredArgsConstructor
public class OauthController {

    private final UserService userService;

    private final KakaoLoginService kakaoLoginService;

    //클라이언트가 해당 엔드포인트에 접근하면, kakaoLoginUri로 리디렉션하여 사용자를 카카오 로그인 페이지로 보냅니다.
    @GetMapping("/kakao/login")
    public void redirectToKakaoLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 새로운 로직을 추가하여 환경을 판단하고 redirect를 수행합니다.
        String referer = request.getHeader("Referer");
        String redirectUrl;

        // 호스트에 따라 환경 분기 처리
        if (referer != null && referer.startsWith("http://localhost:3000")) {
            // 로컬 환경 처리
            redirectUrl = "https://kauth.kakao.com/oauth/authorize?client_id=9f5309f7fc6b371a2a96d9cfdbd304cd&redirect_uri=http://3.34.236.224:3000/oauth/kakao/login&response_type=code";
        } else {
            // 배포 환경 또는 기본적으로 설정할 환경 처리
            redirectUrl = "https://kauth.kakao.com/oauth/authorize?client_id=9f5309f7fc6b371a2a96d9cfdbd304cd&redirect_uri=http://3.34.236.224:3000/oauth/kakao/login&response_type=code";
        }

        response.sendRedirect(redirectUrl);
    }

    //카카오 소셜 로그인 구현
//    @GetMapping("/oauth/kakao/login")
//    public void kakaoLogin(@RequestParam(name = "code", required = false) String authCode, HttpServletResponse response)
//            throws IOException {
//
//        LoginResultDto loginResult = kakaoLoginService.handleKakaoLogin(authCode);
//        boolean isNewUser = loginResult.isNewUser();
//
//        Cookie authorization = new Cookie("Authorization", loginResult.getToken());
//        authorization.setSecure(false); // HTTPS 연결에서만 쿠키 전송 localhost에서는 허용됨
//        authorization.setHttpOnly(true); // JavaScript에서 접근 불가
//        authorization.setPath("/"); // 전체 경로에 대해 쿠키 적용
//        authorization.setMaxAge(3600); // 1시간 동안 유효
//        response.addCookie(authorization);
//
//        //String redirectUrl = isNewUser? myPageUrl : mainPageUrl;
//        String redirectUrl = "/";
//
//        // response.sendRedirect("https://www.turu-parking.com");
//        response.sendRedirect(redirectUrl);
//    }

    @GetMapping("/oauth/kakao/login")
    public ResponseEntity<Void> kakaoLogin(@RequestParam(name = "code", required = false) String authCode,
                               HttpServletResponse response, HttpServletRequest request, Model model)

            throws IOException {

        System.out.println("-------------인가코드"+authCode);

        LoginResultDto loginResult = kakaoLoginService.handleKakaoLogin(authCode);
        boolean isNewUser = loginResult.isNewUser();
        System.out.println("-------------토큰"+loginResult.getToken());

        // 쿠키 생성
        Cookie authorization = new Cookie("Authorization", loginResult.getToken());
        authorization.setSecure(false); // HTTPS 연결에서만 쿠키 전송 localhost에서는 허용됨
        authorization.setHttpOnly(false); // JavaScript에서 접근 불가=백엔드에서만 접근 가능 -> false
        authorization.setPath("/"); // 전체 경로에 대해 쿠키 적용
        authorization.setMaxAge(3600); // 1시간 동안 유효
        response.addCookie(authorization);

        // URL 파라미터로 TokenDTO의 정보를 전달
        String redirectUrl = "http://localhost:4000/oauth/kakao/login_progress?token=" + loginResult.getToken() + "&sessionId=" + request.getSession().getId();

        // HttpHeaders에 Location을 설정하여 리다이렉트
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, authorization.getName() + "=" + authorization.getValue()); // 쿠키 설정
        headers.setLocation(URI.create(redirectUrl)); // 리다이렉트할 URL

        return new ResponseEntity<>(headers, HttpStatus.FOUND); // HTTP 상태 코드 302 반환

    }
}
