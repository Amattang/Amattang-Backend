package com.example.amattang.util;

import com.example.amattang.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


@Getter
@AllArgsConstructor
public class UserPrincipal implements UserDetails {


    private String id;
    private String provider;
    private String name;
    private Collection<? extends GrantedAuthority> authorities;


    public static UserPrincipal create(User user) {

        List<GrantedAuthority> authorities = Collections.
                singletonList(new SimpleGrantedAuthority("ROLE_" + User.ROLE.USER.name()));

        return new UserPrincipal(
                user.getId(),
                user.getProvider().name(),
                user.getName(),
                authorities
        );

    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
