package com.humax.parking.service;

import com.humax.parking.dto.*;
import com.humax.parking.model.ParkingEntity;
import com.humax.parking.repository.UserRepository;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<ParkingInfoDTO> findNearbyParking(UserLocationDTO userLocationDTO){
        try{
            double userLatitude = Double.parseDouble(userLocationDTO.getLat());
            double userLongitude = Double.parseDouble(userLocationDTO.getLon());
            int maxDistance = userLocationDTO.getDistance();

            List<ParkingEntity> nearParkingEntities = userRepository.findNearbyParking(
                    userLatitude, userLongitude, maxDistance);

            List<ParkingInfoDTO> parkingInfoDTOs = new ArrayList<>();
            for (ParkingEntity parkingEntity : nearParkingEntities) {
                ParkingInfoDTO parkingInfoDTO = new ParkingInfoDTO();

                parkingInfoDTO.setName(parkingEntity.getName());
                parkingInfoDTO.setAddress(parkingEntity.getAddress());
                parkingInfoDTO.setOperatingTime(parkingEntity.getOperatingTime());
                parkingInfoDTO.setTimeTicket(parkingEntity.getTimeTicket());
                parkingInfoDTO.setNormalSeason(parkingEntity.getNormalSeason());
                parkingInfoDTO.setTenantSeason(parkingEntity.getTenantSeason());
                parkingInfoDTO.setDayTicket(parkingEntity.getDayTicket());
                parkingInfoDTO.setSpecialDay(parkingEntity.getSpecialDay());
                parkingInfoDTO.setSpecialHour(parkingEntity.getSpecialHour());
                parkingInfoDTO.setSpecialNight(parkingEntity.getSpecialNight());
                parkingInfoDTO.setSpecialWeekend(parkingEntity.getSpecialWeekend());
                parkingInfoDTO.setApplyDay(parkingEntity.getApplyDay());
                parkingInfoDTO.setApplyHour(parkingEntity.getApplyHour());
                parkingInfoDTO.setApplyNight(parkingEntity.getApplyNight());
                parkingInfoDTO.setApplyWeekend(parkingEntity.getApplyWeekend());
                parkingInfoDTOs.add(parkingInfoDTO);
            }

            // 검색 횟수 갱신
            updateSearchCount(nearParkingEntities);

            return parkingInfoDTOs;
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("가까운 주차장을 찾지 못했습니다.", e);
        }
    }


    private void updateSearchCount(List<ParkingEntity> parkingEntities){
        for(ParkingEntity parkingEntity : parkingEntities){
            parkingEntity.setSearchCount(parkingEntity.getSearchCount()+1);
        }
    }
}
