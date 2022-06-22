package com.secondhand.ecommerce.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {

    private String title;
    private String version;
    private String description;
}
