package com.humax.parking.config;

import com.humax.parking.common.UserPrincipal;
import com.humax.parking.common.exception.CustomException;
import com.humax.parking.common.util.JwtUtil;
import com.humax.parking.model.User;
import com.humax.parking.repository.OAuthUserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/*
Spring 애플리케이션이 시작될 때 실행
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final OAuthUserRepository oAuthUserRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {

        Cookie[] cookies = request.getCookies();

        String authorization = null;


        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Authorization")) {
                    authorization = cookie.getValue();
                    break;
                }
            }
        }

//        if (authorization == null || !authorization.startsWith("Bearer ")) {
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰 누락");
//            return;
//        }

        //String token = authorization.split(" ")[1];// 원래 공백
        String token = authorization;

        try {
            UserPrincipal userPrincipal = createPrincipalFromToken(token);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userPrincipal, null, List.of(new SimpleGrantedAuthority("USER")));

            authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (ExpiredJwtException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰 만료");
            return;
        } catch (Exception e) {
            logger.error(e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "토큰 오류");
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        List<String> allowedPath = Arrays.asList(
                "/kakao/login",
                "/oauth/kakao/login",
                "/favicon.ico");

        String path = request.getRequestURI();

        return allowedPath.stream().anyMatch(path::equals);
    }

    private UserPrincipal createPrincipalFromToken(String token) {
        User user = oAuthUserRepository.findById(jwtUtil.getUserId(token))
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "회원 조회 실패"));

        return UserPrincipal.builder(user);
    }
}
