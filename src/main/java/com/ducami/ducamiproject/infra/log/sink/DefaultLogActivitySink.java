package com.ducami.ducamiproject.infra.log.sink;

import com.ducami.ducamiproject.domain.admin.log.domain.AdminLogEntity;
import com.ducami.ducamiproject.domain.admin.log.repository.AdminLogRepository;
import com.ducami.ducamiproject.domain.admin.log.service.AdminLogService;
import com.ducami.ducamiproject.infra.log.aop.LogActivityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DefaultLogActivitySink implements LogActivitySink {

    private final AdminLogRepository adminLogRepository;

    @Override
    public void emit(LogActivityContext context) {
        AdminLogEntity log = AdminLogEntity.builder()
                .actionType(context.getAction())
                .details(context.getMessage())
                .actorId(context.getActor().getUserId())
                .build();
        adminLogRepository.save(log);

    }
}
