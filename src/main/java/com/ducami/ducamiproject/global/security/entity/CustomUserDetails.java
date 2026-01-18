package com.ducami.ducamiproject.global.security.entity;


import com.ducami.ducamiproject.domain.user.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final UserEntity userEntity;

    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(UserEntity userEntity, Collection<? extends GrantedAuthority> authorities) {
        this.userEntity = userEntity;
        this.authorities = authorities;
    }

    public CustomUserDetails(UserEntity userEntity) {
        this.userEntity = userEntity;
        this.authorities = List.of(new SimpleGrantedAuthority(userEntity.getRole().name()));
    }

    public UserEntity getUser() {
        return userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }

    public String getEmail() {
        return userEntity.getEmail();
    }

}
