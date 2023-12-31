package com.example.instagramscheduler;


import com.vaadin.flow.component.dependency.NpmPackage;
import jakarta.transaction.SystemException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
public class InstagramSchedulerApplication extends SpringBootServletInitializer {

    public static void main(String[] args) throws SystemException {
        SpringApplication.run(InstagramSchedulerApplication.class, args);
    }
}