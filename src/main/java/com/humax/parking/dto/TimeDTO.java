package com.humax.parking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeDTO {
    private Long parkingId;
    private LocalDateTime createdAt;

    @Builder
    public TimeDTO(Long parkingId) {
        this.parkingId = parkingId;
        this.createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }
}
