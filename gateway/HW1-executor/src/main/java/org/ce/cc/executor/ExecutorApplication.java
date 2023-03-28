package org.ce.cc.executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"org.ce.cc.core", "org.ce.cc.executor"})
@EnableJpaRepositories(basePackages = "org.ce.cc.core.repository")
@EntityScan("org.ce.cc.core.entity")
//@ComponentScan("org.ce.cc.core.service")
//@ComponentScan("org.ce.cc.core.config")
public class ExecutorApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExecutorApplication.class, args);
    }
}
