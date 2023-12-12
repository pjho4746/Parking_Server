package com.humax.parking.service;

import com.humax.parking.model.Bookmark;
import com.humax.parking.model.ParkingEntity;
import com.humax.parking.model.User;
import com.humax.parking.repository.BookmarkRepository;
import com.humax.parking.repository.ParkingRepository;
import com.humax.parking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final ParkingRepository parkingRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addBookmark(Long userId, Long parkingId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        ParkingEntity parkingEntity = parkingRepository.findById(parkingId)
                .orElseThrow(() -> new RuntimeException("주차장을 찾을 수 없습니다."));

        if (isBookmarked(userId, parkingId)) {
            throw new RuntimeException("이미 찜한 주차장입니다.");
        }

        Bookmark bookmark = Bookmark.builder()
                .user(user)
                .parkingEntity(parkingEntity)
                .build();

        bookmarkRepository.save(bookmark);
    }

    @Transactional
    public void removeBookmark(Long userId, Long parkingId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        ParkingEntity parkingEntity = parkingRepository.findById(parkingId)
                .orElseThrow(() -> new RuntimeException("주차장을 찾을 수 없습니다."));

        Bookmark bookmark = bookmarkRepository.findByUserAndParkingEntity(user, parkingEntity)
                .orElseThrow(() -> new RuntimeException("찜하지 않은 주차장입니다."));

        bookmarkRepository.delete(bookmark);
    }

    public boolean isBookmarked(Long userId, Long parkingId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        ParkingEntity parkingEntity = parkingRepository.findById(parkingId)
                .orElseThrow(() -> new RuntimeException("주차장을 찾을 수 없습니다."));

        return bookmarkRepository.existsByUserAndParkingEntity(user, parkingEntity);
    }
}
