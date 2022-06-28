package com.secondhand.ecommerce.models.dto.users;

import com.secondhand.ecommerce.models.entity.Address;
import com.secondhand.ecommerce.models.entity.AppUsers;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.Validate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Builder
@Data
public final class AppUserBuilder implements UserDetails {

    private Long userId;
    private String fullName;
    private String email;
    private String password;
    private Address address;
    private Date joinDate;
    private String phoneNumber;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    private Collection<GrantedAuthority> authorities;

    public static AppUserBuilder buildUserDetails(AppUsers user) {
        Validate.notNull(user, "User must not be null");
        Set<GrantedAuthority> authorities = new HashSet<>();
        user.getRoles()
                .forEach(appRoles -> {
                    if (Objects.nonNull(appRoles.getRoleNames())) {
                        authorities.add(new SimpleGrantedAuthority(appRoles.getRoleNames().name()));
                    }
                });

        return AppUserBuilder.builder()
                .userId(user.getUserId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .password(user.getPassword())
                .address(user.getAddress())
                .email(user.getUsername())
                .phoneNumber(user.getPhoneNumber())
                .joinDate(user.getJoinDate())
                .accountNonExpired(user.isAccountNonExpired())
                .accountNonLocked(user.isAccountNonLocked())
                .credentialsNonExpired(user.isCredentialsNonExpired())
                .enabled(user.isEnabled())
                .authorities(authorities)
                .build();

    }

    @Override
    public String getUsername() {
        return email;
    }
}
