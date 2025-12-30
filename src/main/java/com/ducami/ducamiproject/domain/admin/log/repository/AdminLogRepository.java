package com.ducami.ducamiproject.domain.admin.log.repository;

import com.ducami.ducamiproject.domain.admin.log.domain.AdminLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminLogRepository extends JpaRepository<AdminLogEntity, Long> {
}
