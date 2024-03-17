package com.pani.auroraojgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author Pani
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class AuroraOjGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuroraOjGatewayApplication.class, args);
    }

}
