package com.secondhand.ecommerce.security;

import com.secondhand.ecommerce.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static com.secondhand.ecommerce.utils.SecondHandConst.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class AppsSecurityConfig {

    private final AppUserService userService;

    private final PasswordEncoder passwordEncoder;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //PUBLIC End points
                .authorizeRequests()
                .antMatchers("/", "index", "/js/*", "/css/*", "/images/*").permitAll()
                .antMatchers("/api/v1/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()

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

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .antMatchers("/images/*.png");
    }

}

