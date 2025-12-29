package com.ducami.ducamiproject.domain.repository;

import com.ducami.ducamiproject.domain.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
