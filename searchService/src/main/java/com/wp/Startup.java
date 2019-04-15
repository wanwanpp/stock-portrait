package com.wp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Created by li on 2019/1/6.
 */
@SpringBootApplication
@EnableEurekaClient
@EnableAutoConfiguration
public class Startup {
    public static void main(String[] args) {

        SpringApplication.run( Startup.class, args );
    }
}
