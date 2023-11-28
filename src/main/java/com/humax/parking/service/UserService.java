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
                parkingInfoDTO.setOperationTime(parkingEntity.getOperatingTime());
                parkingInfoDTO.setTimeTicket(parkingEntity.getTimeTicket());

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

    private List<ParkingDTO>mapEntitiesToDTOs(List<ParkingEntity> parkingEntities){
        return parkingEntities.stream()
                .map(ParkingDTO::new)
                .collect(Collectors.toList());
    }

    private void updateSearchCount(List<ParkingEntity> parkingEntities){
        for(ParkingEntity parkingEntity : parkingEntities){
            parkingEntity.setSearchCount(parkingEntity.getSearchCount()+1);
        }
    }
}
