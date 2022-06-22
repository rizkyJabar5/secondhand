package com.secondhand.ecommerce.models.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "app_users")
public class AppUsers implements UserDetails {

    @Id
    @SequenceGenerator(name = "user_sequence",
            sequenceName = "user_sequence",
            initialValue = 3,
            allocationSize = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "user_sequence")
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Embedded
    private Address address;

    @Column(name = "join_date")
    private Date joinDate;

    @Column(name = "phone_number")
    private Integer phoneNumber;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Collection<AppRoles> roles;


    public AppUsers(String fullname, String email, String password) {
        this.fullName = fullname;
        this.email = email;
        this.password = password;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return roles.stream()
                .map(roles -> new SimpleGrantedAuthority(roles.getRoleNames().name()))
                .collect(Collectors.toList());

    }

    @Override
    public String getUsername() {
        return email;
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
