package com.ducami.ducamiproject.domain.admin.log.service;

import com.ducami.ducamiproject.domain.admin.log.domain.AdminLogEntity;
import com.ducami.ducamiproject.domain.admin.log.dto.response.AdminLogResponse;
import com.ducami.ducamiproject.domain.admin.log.repository.AdminLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
public class AdminLogService {

    private final AdminLogRepository adminLogRepository;

    public Page<AdminLogResponse> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return adminLogRepository.findAllLogs(pageable);
    }


    @Transactional
    public void saveLog(AdminLogEntity logEntity) {
        adminLogRepository.save(logEntity);
    }
}