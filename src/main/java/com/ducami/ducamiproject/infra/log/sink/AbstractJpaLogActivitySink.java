package com.ducami.ducamiproject.infra.log.sink;

import com.ducami.ducamiproject.infra.log.aop.LogActivityContext;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractJpaLogActivitySink<E> implements LogActivitySink {
    
    @Override
    public void emit(LogActivityContext context) {
        E entity = mapToEntity(context);

        JpaRepository<E, ?> repository = getRepository();

        repository.save(entity);
    }

    abstract protected JpaRepository<E, ?> getRepository();

    abstract protected E mapToEntity(LogActivityContext context);
}
