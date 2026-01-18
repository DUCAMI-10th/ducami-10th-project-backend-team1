package com.ducami.ducamiproject.domain.admin.log.repository;

import com.ducami.ducamiproject.domain.admin.log.entity.AdminLogEntity;
import com.ducami.ducamiproject.domain.admin.log.dto.response.AdminLogResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface AdminLogRepository extends JpaRepository<AdminLogEntity, Long> {

    @Query(
        value = """
            select new com.ducami.ducamiproject.domain.admin.log.dto.response.AdminLogResponse(
                a.id,
                a.actionType,
                a.details,
                a.actionTime,
                u.name
                )
            from AdminLogEntity a
            join a.actor u
        """,
        countQuery = """
            select count(a)
            from AdminLogEntity a
        """
    )
    Page<AdminLogResponse> findAllLogs(Pageable pageable);
}
