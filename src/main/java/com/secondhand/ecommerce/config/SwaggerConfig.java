package com.secondhand.ecommerce.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI(@Value("SecondHand API is an API that can perform various operations to manage a transaction. " +
            "The SecondHand API is still under development so if you find any bugs, please report them at the contact below." +
            "Your support means a lot to us") String appDescription,
                                 @Value("v1.0.0") String appVersion){
        return new OpenAPI().info(
                new Info().title("SecondHand")
                        .version(appVersion)
                        .description(appDescription)
                        .termsOfService("http://swagger.io/terms")
                        .contact(new Contact()
                                .name("Andika Rizaldy")
                                .url("https://wa.me/+6285253435963"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdocs.org")

                        )
        );
    }
}