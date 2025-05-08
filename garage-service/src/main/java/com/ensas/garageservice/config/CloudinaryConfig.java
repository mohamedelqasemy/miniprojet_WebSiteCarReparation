package com.ensas.garageservice.config;


import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        final Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dhfpiuaef");
            config.put("api_key", "932592227956343");
        config.put("api_secret", "CfkM4BKVTQ0CWp8leD79tRnweC4");
        return new Cloudinary(config);
    }
}