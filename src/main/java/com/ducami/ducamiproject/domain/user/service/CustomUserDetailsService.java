package com.ducami.ducamiproject.domain.user.service;

import com.ducami.ducamiproject.domain.user.entity.UserEntity;
import com.ducami.ducamiproject.domain.user.repository.UserRepository;
import com.ducami.ducamiproject.global.security.entity.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("유저가 존재하지 않습니다."));
        return new CustomUserDetails(user);
    }
}
