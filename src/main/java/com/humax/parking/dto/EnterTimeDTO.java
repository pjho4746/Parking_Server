package com.humax.parking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnterTimeDTO {
    private Long parkingId;
    private LocalDateTime createdAt;

    @Builder
    public EnterTimeDTO(Long parkingId) {
        this.parkingId = parkingId;
        this.createdAt = LocalDateTime.now();
    }
}