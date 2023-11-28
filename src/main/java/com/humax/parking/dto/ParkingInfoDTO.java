package com.humax.parking.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ParkingInfoDTO {
    private String name;

    private String operationTime;

    private String timeTicket;

    private String distance;
}
