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

    public JwtResponse(Long id,
                       String token,
                       String username,
                       String email,
                       List<GrantedAuthority> roles) {

        this.id = id;
        this.token = token;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public JwtResponse(String token,
                       Long id,
                       String username,
                       String email) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
    }


}
