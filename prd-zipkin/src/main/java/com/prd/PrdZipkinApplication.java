package com.prd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@zipkin.server.internal.EnableZipkinServer
@SpringBootApplication
public class PrdZipkinApplication {

    private static Logger logger = LoggerFactory.getLogger(PrdZipkinApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PrdZipkinApplication.class, args);
    }

}

