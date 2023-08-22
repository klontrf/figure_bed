package com.su.config;


import com.su.controller.interceptor.ProjectInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class SpringmvcConfig implements WebMvcConfigurer {
    @Value("${app.virtual-mapping.dummy}")
    private String dummy;
    @Value("${app.virtual-mapping.location}")
    private String location;
    @Autowired
    private ProjectInterceptor projectInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(projectInterceptor).addPathPatterns("/user/changePassword");
        registry.addInterceptor(projectInterceptor).addPathPatterns("/user/home");
        registry.addInterceptor(projectInterceptor).addPathPatterns("/user/exit");
        registry.addInterceptor(projectInterceptor).addPathPatterns("/user/upload");
        registry.addInterceptor(projectInterceptor).addPathPatterns("/user/del-file");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler(dummy).addResourceLocations(location);
//        pro
//        registry.addResourceHandler("/pic/**").addResourceLocations("file:/root/figure-bed/user-source/");
    }
}