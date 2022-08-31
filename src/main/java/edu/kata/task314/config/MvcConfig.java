package edu.kata.task314.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @PostConstruct
    private void saveUser() {
        //
    }
}