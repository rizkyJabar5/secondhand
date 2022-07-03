package com.secondhand.ecommerce.security.authentication;

import com.secondhand.ecommerce.models.entity.AppRoles;
import com.secondhand.ecommerce.models.enums.ERole;
import com.secondhand.ecommerce.repository.AppRolesRepository;
import com.secondhand.ecommerce.utils.HasLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.secondhand.ecommerce.utils.SecondHandConst.ROLE_IS_NOT_FOUND_MSG;


@Configuration
public class AuthenticatedUser implements HasLogger {

    private final AppRolesRepository repository;

    public AuthenticatedUser(AppRolesRepository repository) {
        this.repository = repository;
    }

    /**
     * Menyiapkan role untuk user jika belum ada di database maka
     * method ini akan dieksekusi.
     */

    @Bean
    public void preRun() {

        for (ERole eRole : ERole.values()) {
            try {
                AppRoles role = repository.findByRoleNames(eRole)
                        .orElseThrow(() -> new RuntimeException(String.format(ROLE_IS_NOT_FOUND_MSG, eRole)));
                getLogger().info("{} is found", role);
            } catch (RuntimeException e) {
                getLogger().info(String.format(ROLE_IS_NOT_FOUND_MSG + ". It will be create ...", eRole.name()));

                AppRoles roleName = new AppRoles();
                roleName.setRoleNames(eRole);
                repository.save(roleName);
            }
        }
    }

}
