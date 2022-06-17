package com.secondhand.ecommerce.security.authentication.login;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class JwtResponse {

    private Long id;
    private String token;
    private String username;
    private String email;
    private List<GrantedAuthority> roles;


}
