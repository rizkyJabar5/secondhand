package com.secondhand.ecommerce.security.jwt;

import com.google.common.base.Strings;
import com.secondhand.ecommerce.security.SecurityUtils;
import com.secondhand.ecommerce.security.config.EncryptionConfig;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final EncryptionConfig encryptionConfig;
    private final UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String jwt = jwtUtils.getJwtToken(request, true);

        if (Strings.isNullOrEmpty(jwt)) {
            jwt = jwtUtils.getJwtToken(request, true);
        }

        if (StringUtils.isNotBlank(jwt)) {
            String accessToken = encryptionConfig.decrypt(jwt);

            if (StringUtils.isNotBlank(accessToken) && jwtUtils.isValidJwtToken(accessToken)) {
                String email = jwtUtils.getUsernameFromToken(accessToken);
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                SecurityUtils.authenticateUser(request, userDetails);
            }
        }
        filterChain.doFilter(request, response);
    }


}
