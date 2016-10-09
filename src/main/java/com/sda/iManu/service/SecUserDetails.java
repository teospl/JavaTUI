package com.sda.iManu.service;

import com.sda.iManu.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Spring Security Wrapper for User object.
 * @see User
 */
public class SecUserDetails  implements UserDetails {

    public User userData;

    public SecUserDetails(final User userData) {
        this.userData = userData;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new LinkedList<>();
        GrantedAuthority user = new SimpleGrantedAuthority(userData.getRole().toString());
        authorities.add(user);
        return authorities;
    }

    @Override
    public String getPassword() {
        return userData.getPassword();
    }

    @Override
    public String getUsername() {
        return userData.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }



}
