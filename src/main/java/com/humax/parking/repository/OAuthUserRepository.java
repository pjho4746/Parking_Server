package com.humax.parking.repository;

import com.humax.parking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OAuthUserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
