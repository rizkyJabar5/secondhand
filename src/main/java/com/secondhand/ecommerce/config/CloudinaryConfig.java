package com.secondhand.ecommerce.config;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class CloudinaryConfig {

    @Value("${cloud.name}")
    private String cloudName;
    @Value("${cloud.key}")
    private String apiKey;
    @Value("${cloud.secret}")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary() {

        Map<String, Object> config = new HashMap<>();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);

        return new Cloudinary(config);
    }

    public Map upload(Object file, Map options) {

        try {
            return cloudinary().uploader().upload(file, options);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map delete(String file, Map options) {
        try {
            return cloudinary().uploader().destroy(file, options);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
