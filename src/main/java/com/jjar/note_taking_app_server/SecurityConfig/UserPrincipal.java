package com.jjar.note_taking_app_server.SecurityConfig;

import com.jjar.note_taking_app_server.Entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserPrincipal implements UserDetails {

    private final User user;

    public UserPrincipal(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Implement account expiration logic if needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Implement account locking logic if needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Implement credential expiration logic if needed
    }

    @Override
    public boolean isEnabled() {
        return true; // Implement account enabling logic if needed
    }
}
