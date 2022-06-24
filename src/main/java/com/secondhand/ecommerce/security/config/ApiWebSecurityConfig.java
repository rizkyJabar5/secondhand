package com.secondhand.ecommerce.security.config;

import com.secondhand.ecommerce.security.jwt.AuthEntryPointJwt;
import com.secondhand.ecommerce.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.secondhand.ecommerce.utils.SecondHandConst.*;

@Configuration
@RequiredArgsConstructor
public class ApiWebSecurityConfig {

    private final AuthenticationManager authenticationManager;
    private final AuthEntryPointJwt authEntryPointJwt;
    private final JwtAuthenticationFilter authenticationFilter;


    @Bean
    @Order(1)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors();

        http.csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(authEntryPointJwt)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //PUBLIC End points
        http.authorizeRequests()
                .antMatchers("/api/v1/auth/**",
                        SWAGGER_API, SWAGGER_API_DOCS, HOME_PAGE).permitAll()

                //Authenticated request
                .anyRequest()
                .authenticated()
                .and()

                //Configure for form Login
                .formLogin()
                .loginPage(LOGIN_URL)
                .defaultSuccessUrl(HOME_PAGE, true)
                .failureUrl(LOGIN_FAILURE_URL).permitAll()

                //Logout Handler
                .and()
                .logout()
                .logoutSuccessUrl(LOGOUT);

        http.authenticationManager(authenticationManager);

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
