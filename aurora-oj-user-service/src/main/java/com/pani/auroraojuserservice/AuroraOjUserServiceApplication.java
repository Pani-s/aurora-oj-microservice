package com.pani.auroraojuserservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Pani
 */
@MapperScan("com.pani.auroraojuserservice.mapper")
//@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@SpringBootApplication
@ComponentScan("com.pani")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.pani.auroraojserviceclient.service"})
public class AuroraOjUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuroraOjUserServiceApplication.class, args);
    }

}
