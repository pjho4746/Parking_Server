package com.humax.parking.repository;

import com.humax.parking.model.Bookmark;
import com.humax.parking.model.ParkingEntity;
import com.humax.parking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    boolean existsByUserAndParkingEntity(User user, ParkingEntity parkingEntityg);
    Optional<Bookmark> findByUserAndParkingEntity(User user, ParkingEntity parkingEntity);
}
