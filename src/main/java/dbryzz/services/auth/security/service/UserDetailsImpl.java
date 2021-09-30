package dbryzz.services.auth.security.service;

import dbryzz.services.auth.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsImpl extends User implements UserDetails {

    public UserDetailsImpl(final User user) {
        super(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return super.getActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return super.getIsEmailVerified();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        UserDetailsImpl that = (UserDetailsImpl) obj;
        return Objects.equals(getId(), that.getId());
    }
}
