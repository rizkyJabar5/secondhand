package com.secondhand.ecommerce.security.authentication;

import com.secondhand.ecommerce.models.entity.AppRoles;
import com.secondhand.ecommerce.models.enums.ERole;
import com.secondhand.ecommerce.repository.AppRolesRepository;
import com.secondhand.ecommerce.utils.HasLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


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
                        .orElseThrow(() -> new RuntimeException("Roles not found"));
                getLogger().error("{} not found", role);
            } catch (RuntimeException e) {
                String msg = "Role " + eRole.name() + " is not found. Please create one...";
                getLogger().info(msg);

                AppRoles roleName = new AppRoles();
                roleName.setRoleNames(eRole);
                repository.save(roleName);
            }
        }
    }

}
