package com.humax.parking.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "enter_data")
public class Enter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enter_id", nullable = false, unique = true)
    private Long enterId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parking_id", nullable = false)
    private ParkingEntity parkingEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;


    @Column(name="car_number", nullable = false)
    private String carNumber;

    @Column(name="entry_time", nullable = false)
    private LocalDateTime entryTime;

    @Column(name="exit_time", nullable = false)
    private LocalDateTime exitTime;

}
