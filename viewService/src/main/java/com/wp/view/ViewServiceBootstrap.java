package com.wp.view;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.wp.view.client")
public class ViewServiceBootstrap {
    public static void main(String[] args) {

        SpringApplication.run( ViewServiceBootstrap.class, args );
    }
}
