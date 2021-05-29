package hu.fitforfun.security;

import hu.fitforfun.model.user.Authority;
import hu.fitforfun.model.user.Role;
import hu.fitforfun.model.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

public class UserPrincipal implements UserDetails {

    private User user;
    private Long userId;

    public UserPrincipal(User user) {
        this.user = user;
        this.userId = user.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new HashSet<>();
        Collection<Authority> authoritiesEntity = new HashSet<>();
        Collection<Role> roles = user.getRoles();
        if (roles == null) {
            return authorities;
        }
        roles.forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            authoritiesEntity.addAll(role.getAuthorities());
        });

        authoritiesEntity.forEach(authority -> {
            authorities.add(new SimpleGrantedAuthority(authority.getName()));
        });
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getContactData().getEmail();
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
        return user.getEmailVerificationStatus();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
