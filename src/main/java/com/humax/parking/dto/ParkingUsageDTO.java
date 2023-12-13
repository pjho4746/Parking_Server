package com.humax.parking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ParkingUsageDTO {
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

    private Long usageMinutes;
}
