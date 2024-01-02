package com.example.instagramscheduler;


import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import jakarta.transaction.SystemException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@Theme(value = "intagramscheduler.v.2.0")
public class InstagramSchedulerApplication implements AppShellConfigurator {

    public static void main(String[] args) throws SystemException {
        SpringApplication.run(InstagramSchedulerApplication.class, args);
    }
}