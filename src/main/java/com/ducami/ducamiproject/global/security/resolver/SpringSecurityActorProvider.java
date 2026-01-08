package com.ducami.ducamiproject.global.security.resolver;

import com.ducami.ducamiproject.global.log.entity.Actor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
//TODO: 폴더위치 옮기기
//TODO: 코드 Bean 등록
//TODO: Guest도 가능한지 확인코드
//TODO: advice - service 사이 중간로직 필요
public abstract class SpringSecurityActorProvider<U extends UserDetails, A extends Actor> implements ActorProvider<A> {

    private final Class<U> detailsType;

    protected SpringSecurityActorProvider(Class<U> detailsType) {
        this.detailsType = detailsType;
    }

    protected U getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        if ((detailsType.isInstance(principal))) {
            return detailsType.cast(principal);
        }
        return null;
    }
}
