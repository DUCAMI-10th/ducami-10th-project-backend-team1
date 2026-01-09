package com.ducami.ducamiproject.global.log.sink;

import com.ducami.ducamiproject.domain.admin.log.service.AdminLogService;
import com.ducami.ducamiproject.global.log.aop.LogActivityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Role(BeanDefinition.ROLE_INFRASTRUCTURE) // Added @Role
public class DefaultLogActivitySink implements LogActivitySink {

    private final AdminLogService adminLogService;

    @Override
    public void emit(LogActivityContext context) {
        adminLogService.saveLog(context);
    }
}
