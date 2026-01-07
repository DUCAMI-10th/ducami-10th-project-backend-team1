package com.ducami.ducamiproject.global.log.resolver;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
//TODO: 폴더위치 옮기기, 파라미터 잘 불러와지는지 한번 테스트, actorProvider 테스트
public abstract class SpringSecurityActorProvider<U extends UserDetails, A> implements ActorProvider<A> {

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

        // 익명 사용자(anonymousUser)인 경우 String이 들어오므로 체크가 필요합니다.
        if ((detailsType.isInstance(principal))) {
            return detailsType.cast(principal);
        }

        return null;
    }
}
