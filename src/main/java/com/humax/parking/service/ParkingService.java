package com.humax.parking.service;

import com.humax.parking.common.util.JwtUtil;
import com.humax.parking.dto.ParkingDTO;
import com.humax.parking.dto.ParkingInfoDTO;
import com.humax.parking.exception.DuplicateException;
import com.humax.parking.exception.NotFoundException;
import com.humax.parking.model.Bookmark;
import com.humax.parking.model.ParkingEntity;
import com.humax.parking.model.User;
import com.humax.parking.repository.BookmarkRepository;
import com.humax.parking.repository.ParkingRepository;
import com.humax.parking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ParkingService {

    private final ParkingRepository parkingRepository;

    private final BookmarkRepository bookmarkRepository;

    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;

    @Transactional
    public void createParkingInfo(ParkingDTO parkingDTO) {
        ParkingEntity parkingEntity = parkingRepository.findByCodeNumber(parkingDTO.getCodeNumber());
        if (parkingEntity != null) { // 이미 저장된 값이 있다면 에러 반환
            throw new DuplicateException("중복된 코드 넘버가 존재합니다.");
        } else {
            // 해당 코드 넘버를 가진 ParkingEntity가 없는 경우에만 새로운 엔티티 생성하여 저장
            parkingEntity = parkingDTO.toEntity();
            parkingRepository.save(parkingEntity);
        }
    }

    public void setIsBookmark(User user, List<ParkingDTO> parkingDTOS){
        List<Bookmark> bookmarks = bookmarkRepository.findBookmarkByUser(user);

        for(ParkingDTO parkingDTO : parkingDTOS){
            long parkingId = parkingDTO.getParkingId();
            boolean isBookmark = bookmarks.stream().anyMatch(bookmark -> bookmark.getParkingEntity().getParkingId() == parkingId);
            parkingDTO.setStatusBookmark(isBookmark ? 1: 0);
        }

    }

    @Transactional
    public List<ParkingDTO> getParkingInfo(String token) {
        Long userId = jwtUtil.getUserId(token);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

        List<ParkingEntity> parkingEntities = parkingRepository.findAll();
        List<ParkingDTO> parkingDTOs = new ArrayList<>();

        for(ParkingEntity parkingEntity: parkingEntities){
            ParkingDTO parkingDTO = createParkingDTO(parkingEntity);
            parkingDTOs.add(parkingDTO);
        }
        setIsBookmark(user, parkingDTOs);

        return parkingDTOs;
    }

    @Transactional
    public ParkingDTO createParkingDTO(ParkingEntity parkingEntity){
        Long parkingId = parkingEntity.getParkingId();
        return ParkingDTO.builder()
                .parkingId(parkingEntity.getParkingId())
                .codeNumber(parkingEntity.getCodeNumber())
                .name(parkingEntity.getName())
                .address(parkingEntity.getAddress())
                .lat(parkingEntity.getLat())
                .lon(parkingEntity.getLon())
                .operatingTime(parkingEntity.getOperatingTime())
                .normalSeason(parkingEntity.getNormalSeason())
                .tenantSeason(parkingEntity.getTenantSeason())
                .timeTicket(parkingEntity.getTimeTicket())
                .dayTicket(parkingEntity.getDayTicket())
                .specialDay(parkingEntity.getSpecialDay())
                .specialHour(parkingEntity.getSpecialHour())
                .specialNight(parkingEntity.getSpecialNight())
                .specialWeekend(parkingEntity.getSpecialWeekend())
                .applyHour(parkingEntity.getApplyHour())
                .applyNight(parkingEntity.getApplyNight())
                .applyWeekend(parkingEntity.getApplyWeekend())
                .isActive(parkingEntity.getIsActive())
                .operation(parkingEntity.getOperation())
                .createdAt(parkingEntity.getCreatedAt())
                .deletedAt(parkingEntity.getDeletedAt())
                .time(parkingEntity.getTime())
                .price(parkingEntity.getPrice())
                .deleteYn(false)
                .searchCount(parkingEntity.getSearchCount())
                .build();
    }

    @Transactional
    public ParkingDTO getParkingDetail(Long parking_id)  {
        Optional<ParkingEntity> parkingEntityOptional = parkingRepository.findByParkingId(parking_id);
        if(parkingEntityOptional.isPresent()){
            ParkingDTO parkingDTO = new ParkingDTO(parkingEntityOptional.get());
            return parkingDTO;
        }
        else{
            return null;
        }
    }

    @Transactional
    public void deleteParking(Long parking_id) {
        Optional<ParkingEntity> parkingEntityOptional = parkingRepository.findByParkingId(parking_id);
        if (parkingEntityOptional.isPresent()) {
            ParkingEntity parkingEntity = parkingEntityOptional.get();
            parkingEntity.setDeleteYn(true);
            //parkingRepository.save(parkingEntity);
        }
    }

    @Transactional
    public ParkingEntity updateParkingInfo(ParkingEntity existingParking, ParkingDTO parkingDTO){
        existingParking.setCodeNumber(Optional.ofNullable(parkingDTO.getCodeNumber()).orElse(existingParking.getCodeNumber()));
        existingParking.setName(Optional.ofNullable(parkingDTO.getName()).orElse(existingParking.getName()));
        existingParking.setAddress(Optional.ofNullable(parkingDTO.getAddress()).orElse(existingParking.getAddress()));
        existingParking.setLat(Optional.ofNullable(parkingDTO.getLat()).orElse(existingParking.getLat()));
        existingParking.setLon(Optional.ofNullable(parkingDTO.getLon()).orElse(existingParking.getLon()));
        existingParking.setOperatingTime(Optional.ofNullable(parkingDTO.getOperatingTime()).orElse(existingParking.getOperatingTime()));
        existingParking.setNormalSeason(Optional.ofNullable(parkingDTO.getNormalSeason()).orElse(existingParking.getNormalSeason()));
        existingParking.setTenantSeason(Optional.ofNullable(parkingDTO.getTenantSeason()).orElse(existingParking.getTenantSeason()));
        existingParking.setTimeTicket(Optional.ofNullable(parkingDTO.getTimeTicket()).orElse(existingParking.getTimeTicket()));
        existingParking.setDayTicket(Optional.ofNullable(parkingDTO.getDayTicket()).orElse(existingParking.getDayTicket()));
        existingParking.setSpecialDay(Optional.ofNullable(parkingDTO.getSpecialDay()).orElse(existingParking.getSpecialDay()));
        existingParking.setSpecialHour(Optional.ofNullable(parkingDTO.getSpecialHour()).orElse(existingParking.getSpecialHour()));
        existingParking.setSpecialNight(Optional.ofNullable(parkingDTO.getSpecialNight()).orElse(existingParking.getSpecialNight()));
        existingParking.setSpecialWeekend(Optional.ofNullable(parkingDTO.getSpecialWeekend()).orElse(existingParking.getSpecialWeekend()));
        existingParking.setApplyHour(Optional.ofNullable(parkingDTO.getApplyHour()).orElse(existingParking.getApplyHour()));
        existingParking.setApplyNight(Optional.ofNullable(parkingDTO.getApplyNight()).orElse(existingParking.getApplyNight()));
        existingParking.setApplyWeekend(Optional.ofNullable(parkingDTO.getApplyWeekend()).orElse(existingParking.getApplyWeekend()));
        existingParking.setIsActive(Optional.ofNullable(parkingDTO.getIsActive()).orElse(existingParking.getIsActive()));
        existingParking.setOperation(Optional.ofNullable(parkingDTO.getOperation()).orElse(existingParking.getOperation()));
        existingParking.setCreatedAt(Optional.ofNullable(parkingDTO.getCreatedAt()).orElse(existingParking.getCreatedAt()));
        existingParking.setDeletedAt(Optional.ofNullable(parkingDTO.getDeletedAt()).orElse(existingParking.getDeletedAt()));
        existingParking.setTime(Optional.ofNullable(parkingDTO.getTime()).orElse(existingParking.getTime()));
        existingParking.setPrice(Optional.ofNullable(parkingDTO.getPrice()).orElse(existingParking.getPrice()));
        existingParking.setSearchCount(Optional.ofNullable(parkingDTO.getSearchCount()).orElse(existingParking.getSearchCount()));
        return existingParking;
    }
}