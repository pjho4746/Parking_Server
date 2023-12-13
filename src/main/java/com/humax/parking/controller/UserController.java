package com.humax.parking.controller;


import com.humax.parking.dto.*;
import com.humax.parking.repository.ParkingRepository;
import com.humax.parking.service.BookmarkService;
import com.humax.parking.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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
    public ResponseEntity<String> addBookmark(@RequestHeader("Authorization") String token, @RequestParam Long parkingId) {
        bookmarkService.addBookmark(token, parkingId);
        return ResponseEntity.status(HttpStatus.OK).body("찜 완료");
    }

    @PostMapping("/remove")
    public ResponseEntity<String> removeBookmark(@RequestHeader("Authorization") String token, @RequestParam Long parkingId) {
        bookmarkService.removeBookmark(token, parkingId);
        return ResponseEntity.status(HttpStatus.OK).body("찜 해제 완료");
    }

    // 즐겨찾기한 주차장 리스트 조회
    @GetMapping("/bookmark/list")
    public ResponseEntity<List<ParkingInfoDTO>> getBookmarkList(@RequestHeader("Authorization") String token){
        return ResponseEntity.status(HttpStatus.OK).body(bookmarkService.getBookmarkList(token));
        //return ResponseEntity.ok(bookmarkService.getBookmarkList(token));
    }

    // 입차 시간 기록
    @PostMapping("/enter")
    public ResponseEntity<LocalDateTime> enterParkingTime(@RequestHeader("Authorization") String token, @RequestBody TimeDTO enterTimeDTO){
        return ResponseEntity.status(HttpStatus.OK).body(userService.saveEnterTime(token, enterTimeDTO.getParkingId(), enterTimeDTO.getCreatedAt()));
    }

    // 출차 시간 기록
    @PostMapping("/out")
    public ResponseEntity<LocalDateTime> outParkingTime(@RequestHeader("Authorization") String token, @RequestBody TimeDTO outTimeDTO){
        return ResponseEntity.status(HttpStatus.OK).body(userService.saveOutTime(token, outTimeDTO.getParkingId(), outTimeDTO.getCreatedAt()));
    }

    // 주차장 이용 시간 조회
//    @PostMapping("/parking/use")
//    public ResponseEntity<String> getParkingUsage(@RequestHeader("Authorization") String token, @RequestBody Long parkingId){
//        try{
//            String resultMessage = userService.getParkingUsage(token, parkingId);
//            return ResponseEntity.status(HttpStatus.OK).body(resultMessage);
//        } catch(RuntimeException e){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//        } catch (Exception e){
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
//        }
//    }

    @PostMapping("/parking/use")
    public ResponseEntity<?> getParkingUsage(@RequestHeader("Authorization") String token, @RequestBody Long parkingId) {
        try {
            ParkingUsageDTO parkingUsageDTO = userService.getParkingUsage(token, parkingId);
            return ResponseEntity.status(HttpStatus.OK).body(parkingUsageDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
        }
    }
}