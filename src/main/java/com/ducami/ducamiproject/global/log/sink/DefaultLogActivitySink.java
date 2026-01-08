package com.ducami.ducamiproject.global.log.sink;

import com.ducami.ducamiproject.domain.admin.log.service.AdminLogService;
import com.ducami.ducamiproject.global.log.aop.LogActivityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultLogActivitySink implements LogActivitySink {

    private final AdminLogService adminLogService;

    @Override
    public void emit(LogActivityContext context) {
        adminLogService.saveLog(context);
    }
}
