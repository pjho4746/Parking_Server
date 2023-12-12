package com.humax.parking.repository;

import com.humax.parking.model.Bookmark;
import com.humax.parking.model.Enter;
import com.humax.parking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnterRepository extends JpaRepository<Enter, Long> {
    Enter findByUser(User user);
}
