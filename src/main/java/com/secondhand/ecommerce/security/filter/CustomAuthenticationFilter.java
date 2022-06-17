package com.secondhand.ecommerce.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.secondhand.ecommerce.models.entity.AppUsers;
import com.secondhand.ecommerce.security.authentication.login.LoginRequest;
import com.secondhand.ecommerce.utils.HasLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;

import static com.secondhand.ecommerce.utils.SecondHandConst.EXPIRATION_TIME_JWT;

@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter
        implements HasLogger {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(request.getParameter("email"));
        loginRequest.setPassword(request.getParameter("password"));
        getLogger().info("Email is{} ", loginRequest.getEmail());
        getLogger().info("password is{} ", loginRequest.getPassword());


        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword());

        return authenticationManager.authenticate(authenticationToken);

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authentication) throws IOException, ServletException {

        AppUsers user = (AppUsers) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes(StandardCharsets.UTF_8));
        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME_JWT))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", Collections.singletonList(user.getAuthorities()))
                .sign(algorithm);
        String refreshToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME_JWT))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
        response.setHeader("Access Token", accessToken);
        response.setHeader("Refresh Token", refreshToken);
    }
}
