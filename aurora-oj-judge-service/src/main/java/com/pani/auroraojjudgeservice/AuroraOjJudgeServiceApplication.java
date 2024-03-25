package com.pani.auroraojjudgeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author Pani
 */
//@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@SpringBootApplication
@ComponentScan("com.pani")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.pani.auroraojserviceclient.service"})
public class AuroraOjJudgeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuroraOjJudgeServiceApplication.class, args);
    }

}
