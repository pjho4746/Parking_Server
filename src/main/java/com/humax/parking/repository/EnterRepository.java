package com.humax.parking.repository;

import com.humax.parking.model.Bookmark;
import com.humax.parking.model.Enter;
import com.humax.parking.model.ParkingEntity;
import com.humax.parking.model.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EnterRepository extends JpaRepository<Enter, Long> {
    Optional<Enter> findByUser(User user);

    @Transactional
    @Modifying
    @Query("UPDATE Enter e SET e.exitTime = :exitTime WHERE e.enterId = :id")
    void updateExitTimeById(@Param("exitTime") LocalDateTime exitTime, @Param("id") Long id);

    Enter findByUserAndParkingEntity(User user, ParkingEntity parkingEntity);
}
