package com.humax.parking.controller;


import com.humax.parking.dto.*;
import com.humax.parking.model.ParkingEntity;
import com.humax.parking.model.User;
import com.humax.parking.repository.ParkingRepository;
import com.humax.parking.service.BookmarkService;
import com.humax.parking.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ParkingRepository parkingRepository;
    private final BookmarkService bookmarkService;
    @PostMapping("/search")
    public ResponseEntity<List<ParkingInfoDTO>> getNearParking(@RequestBody UserLocationDTO userLocationDTO){
        try{
            List<ParkingInfoDTO> nearbyParking = userService.findNearbyParking(userLocationDTO);
            return ResponseEntity.status(HttpStatus.OK).body(nearbyParking);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/read/list")
    public ResponseEntity<List<ParkingInfoDTO>> getParkingInfoForUI() {
        try {
            List<ParkingInfoDTO> parkingInfoList = userService.getParkingInfo();
            return ResponseEntity.status(HttpStatus.OK).body(parkingInfoList);
        } catch (Exception e) {
            log.error("주차장 정보 목록을 가져오는 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/read/detail/{parking_id}")
    public ResponseEntity<ParkingInfoDTO> getParkingDetail(@PathVariable("parking_id") Long parkingId) {
        try {
            ParkingInfoDTO parkingDetail = userService.getParkingDetail(parkingId);
            return ResponseEntity.status(HttpStatus.OK).body(parkingDetail);
        } catch (Exception e) {
            log.error("주차장 상세 정보를 가져오는 중 오류 발생: {}", parkingId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addBookmark(@RequestParam Long userId, @RequestParam Long parkingId) {
        bookmarkService.addBookmark(userId, parkingId);
        return ResponseEntity.ok("찜 완료");
    }

    @PostMapping("/remove")
    public ResponseEntity<String> removeBookmark(@RequestParam Long userId, @RequestParam Long parkingId) {
        bookmarkService.removeBookmark(userId, parkingId);
        return ResponseEntity.ok("찜 해제 완료");
    }
}