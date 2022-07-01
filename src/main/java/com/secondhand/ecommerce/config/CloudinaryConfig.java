//package com.secondhand.ecommerce.config;
//
//import com.cloudinary.Cloudinary;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//@ConfigurationProperties(prefix = "cloudinary.config")
//public class CloudinaryConfig {
//
//    @Value("${cloud.name}")
//    private String cloudName;
//    @Value("${cloud.key}")
//    private String apiKey;
//    @Value("${cloud.secret}")
//    private String apiSecret;
//
//    @Bean
//    public Cloudinary cloudinaryConfig() {
//
//        Map<String, Object> config = new HashMap<>();
//        config.put("cloud_name", cloudName);
//        config.put("api_key", apiKey);
//        config.put("api_secret", apiSecret);
//
//        return new Cloudinary(config);
//    }
//
//
//}
