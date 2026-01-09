package com.ducami.ducamiproject.domain.admin.log.service;

import com.ducami.ducamiproject.domain.admin.log.dto.response.AdminLogResponse;
import com.ducami.ducamiproject.infra.log.aop.LogActivityContext;
import org.springframework.data.domain.Page;

public interface AdminLogService {

    Page<AdminLogResponse> findAll(int page, int size);

    void saveLog(LogActivityContext context);

}
