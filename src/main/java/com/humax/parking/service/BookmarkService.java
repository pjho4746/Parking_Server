package com.humax.parking.service;

import com.humax.parking.common.util.JwtUtil;
import com.humax.parking.model.Bookmark;
import com.humax.parking.dto.*;
import com.humax.parking.model.ParkingEntity;
import com.humax.parking.model.User;
import com.humax.parking.repository.BookmarkRepository;
import com.humax.parking.repository.ParkingRepository;
import com.humax.parking.repository.UserRepository;
import com.humax.parking.service.kakao.ExchangeKakaoAccessToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private  final ExchangeKakaoAccessToken exchangeKakaoAccessToken;
    private final BookmarkRepository bookmarkRepository;
    private final ParkingRepository parkingRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public void addBookmark(String token, Long parkingId) {
        Long userId = extractUserIdFromToken(token);

        ParkingEntity parkingEntity = parkingRepository.findById(parkingId)
                .orElseThrow(() -> new RuntimeException("주차장을 찾을 수 없습니다."));

        if (isBookmarked(userId, parkingId)) {
            throw new RuntimeException("이미 찜한 주차장입니다.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Bookmark bookmark = Bookmark.builder()
                .user(user)
                .parkingEntity(parkingEntity)
                .build();

        bookmarkRepository.save(bookmark);
    }

    @Transactional
    public void removeBookmark(String token, Long parkingId) {
        Long userId = extractUserIdFromToken(token);

        ParkingEntity parkingEntity = parkingRepository.findById(parkingId)
                .orElseThrow(() -> new RuntimeException("주차장을 찾을 수 없습니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Bookmark bookmark = bookmarkRepository.findByUserAndParkingEntity(user, parkingEntity)
                .orElseThrow(() -> new RuntimeException("찜하지 않은 주차장입니다."));

        bookmarkRepository.delete(bookmark);
    }

    public boolean isBookmarked(Long userId, Long parkingId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        ParkingEntity parkingEntity = parkingRepository.findById(parkingId)
                .orElseThrow(() -> new RuntimeException("주차장을 찾을 수 없습니다."));

        return bookmarkRepository.existsByUserAndParkingEntity(user, parkingEntity);
    }

    // 토큰에서 UserID 가져옴
    private Long extractUserIdFromToken(String token){
        return jwtUtil.getUserId(token);
    }

    @Transactional
    public List<ParkingInfoDTO> getBookmarkList(String token){
        Long userId = extractUserIdFromToken(token);
        List<Bookmark> bookmarks = bookmarkRepository.findBookmarkByUser(userId);

        List<ParkingInfoDTO> parkingInfoDTOS = new ArrayList<>();
        for(Bookmark bookmark : bookmarks){
            if(bookmark.getBookmarkId()==null){
                throw new RuntimeException("즐겨찾기한 주차장을 찾을 수 없습니다. ");
            }

            ParkingEntity parkingEntity = bookmark.getParkingEntity();
            ParkingInfoDTO parkingInfoDTO = new ParkingInfoDTO();

            // ParkingInfoDTO에 ParkingEntity 정보를 설정
            parkingInfoDTO.setParkingId(parkingEntity.getParkingId());
            parkingInfoDTO.setName(parkingEntity.getName());
            parkingInfoDTO.setAddress(parkingEntity.getAddress());
            parkingInfoDTO.setLat(parkingEntity.getLat());
            parkingInfoDTO.setLon(parkingEntity.getLon());
            parkingInfoDTO.setOperatingTime(parkingEntity.getOperatingTime());
            parkingInfoDTO.setNormalSeason(parkingEntity.getNormalSeason());
            parkingInfoDTO.setTenantSeason(parkingEntity.getTenantSeason());
            parkingInfoDTO.setTimeTicket(parkingEntity.getTimeTicket());
            parkingInfoDTO.setDayTicket(parkingEntity.getDayTicket());
            parkingInfoDTO.setSpecialDay(parkingEntity.getSpecialDay());
            parkingInfoDTO.setSpecialHour(parkingEntity.getSpecialHour());
            parkingInfoDTO.setSpecialNight(parkingEntity.getSpecialNight());
            parkingInfoDTO.setSpecialWeekend(parkingEntity.getSpecialWeekend());
            parkingInfoDTO.setApplyDay(parkingEntity.getApplyDay());
            parkingInfoDTO.setApplyHour(parkingEntity.getApplyHour());
            parkingInfoDTO.setApplyNight(parkingEntity.getApplyNight());
            parkingInfoDTO.setApplyWeekend(parkingEntity.getApplyWeekend());

            parkingInfoDTOS.add(parkingInfoDTO);
        }
        return parkingInfoDTOS;

    }
}
