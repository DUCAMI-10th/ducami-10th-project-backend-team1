package com.ducami.ducamiproject.infra.log.sink;

import com.ducami.ducamiproject.domain.admin.log.domain.AdminLogEntity;
import com.ducami.ducamiproject.domain.admin.log.repository.AdminLogRepository;
import com.ducami.ducamiproject.domain.admin.log.service.AdminLogService;
import com.ducami.ducamiproject.infra.log.aop.LogActivityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DefaultLogActivitySink extends AbstractJpaLogActivitySink<AdminLogEntity> {

    private final AdminLogRepository adminLogRepository;

    @Override
    protected JpaRepository<AdminLogEntity, ?> getRepository() {
        return adminLogRepository;
    }

    @Override
    protected AdminLogEntity mapToEntity(LogActivityContext context) {
        return AdminLogEntity.builder()
                .actionType(context.getAction())
                .details(context.getMessage())
                .actorId(context.getActor().getUserId())
                .build();
    }
}
