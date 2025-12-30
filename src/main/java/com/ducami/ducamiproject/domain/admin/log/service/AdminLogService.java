package com.ducami.ducamiproject.domain.admin.log.service;

import com.ducami.ducamiproject.domain.admin.log.domain.AdminLogEntity;
import com.ducami.ducamiproject.domain.admin.log.repository.AdminLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AdminLogService {

    private final AdminLogRepository adminLogRepository;

    @Transactional
    public void saveLog(AdminLogEntity logEntity) {
        adminLogRepository.save(logEntity);
    }
}