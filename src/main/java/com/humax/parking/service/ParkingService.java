package com.humax.parking.service;

import com.humax.parking.dto.ParkingDTO;
import com.humax.parking.model.ParkingEntity;
import com.humax.parking.repository.ParkingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ParkingService {

    private final ParkingRepository parkingRepository;

    @Transactional
    public void createParkingInfo(ParkingDTO parkingDTO) {
        ParkingEntity parkingEntity = parkingRepository.findByCodeNumber(parkingDTO.getCodeNumber());
        if (parkingEntity != null) { // 이미 저장된 값이 있다면 에러 반환
            throw new RuntimeException();
        }
        parkingRepository.save(parkingEntity);
    }

    @Transactional
    public List<ParkingDTO> getParkingInfo() {
        List<ParkingEntity> parkingEntities = parkingRepository.findAll();
        List<ParkingDTO> parkingDTOs = new ArrayList<>();

        for(ParkingEntity parkingEntity: parkingEntities){
            ParkingDTO parkingDTO = createParkingDTO(parkingEntity);
            parkingDTOs.add(parkingDTO);
        }
        return parkingDTOs;
    }

    @Transactional
    public ParkingDTO createParkingDTO(ParkingEntity parkingEntity){
        Long parkingId = parkingEntity.getParkingId();
        return ParkingDTO.builder()
                .parkingId(parkingId)
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
}

