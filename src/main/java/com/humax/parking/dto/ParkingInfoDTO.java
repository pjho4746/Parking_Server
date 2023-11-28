package com.humax.parking.dto;

import com.humax.parking.model.ParkingEntity;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class ParkingInfoDTO {
    private String name;
    private String address;
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

}


