package com.humax.parking.dto;

import com.humax.parking.model.ParkingEntity;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class ParkingInfoDTO {
    private Long parkingId;
    private String name;
    private String address;

    private String lat;
    private String lon;

    private String operatingTime;
    private String normalSeason;
    private String tenantSeason;
    private String timeTicket;
    private String dayTicket;
    private String specialDay;
    private String specialHour;
    private String specialNight;
    private String specialWeekend;
    private String applyDay;
    private String applyHour;
    private String applyNight;
    private String applyWeekend;

    public ParkingInfoDTO(){}

//    public ParkingInfoDTO(ParkingEntity parkingEntity) {
//        this.parkingId = parkingEntity.getParkingId();
//        this.name = parkingEntity.getName();
//        this.address = parkingEntity.getAddress();
//        this.lat = parkingEntity.getLat();
//        this.lon = parkingEntity.getLon();
//        this.operatingTime = parkingEntity.getOperatingTime();
//        this.normalSeason = parkingEntity.getNormalSeason();
//        this.tenantSeason = parkingEntity.getTenantSeason();
//        this.timeTicket = parkingEntity.getTimeTicket();
//        this.dayTicket = parkingEntity.getDayTicket();
//        this.specialDay = parkingEntity.getSpecialDay();
//        this.specialHour = parkingEntity.getSpecialHour();
//        this.specialNight = parkingEntity.getSpecialNight();
//        this.specialWeekend = parkingEntity.getSpecialWeekend();
//        this.applyDay = parkingEntity.getApplyDay();
//        this.applyHour = parkingEntity.getApplyHour();
//        this.applyNight = parkingEntity.getApplyNight();
//        this.applyWeekend = parkingEntity.getApplyWeekend();
//    }
}


