package com.ducami.ducamiproject.domain.admin.log.service;

import com.ducami.ducamiproject.domain.admin.log.domain.AdminLogEntity;
import com.ducami.ducamiproject.domain.admin.log.dto.response.AdminLogResponse;
import com.ducami.ducamiproject.global.log.aop.LogActivityContext;
import org.springframework.data.domain.Page;

public interface AdminLogService {

    Page<AdminLogResponse> findAll(int page, int size);

    void saveLog(AdminLogEntity logEntity);

    void saveLog(LogActivityContext context);

}
