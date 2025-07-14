package org.example.app_gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@EnableAsync
@EnableCaching
@EnableScheduling
@EnableJpaAuditing
@EnableTransactionManagement
@EntityScan(basePackages = {"org.example.**"})
@EnableJpaRepositories(basePackages = {"org.example.**"})
//@EnableMongoRepositories(basePackages = {"task.hub.**"})
@SpringBootApplication(scanBasePackages = {"org.example.**"})

public class AppGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppGatewayApplication.class, args);
    }

}
