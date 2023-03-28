package org.ce.cc.notifier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"org.ce.cc.core", "org.ce.cc.notifier"})
@EntityScan("org.ce.cc.core.entity")
@EnableJpaRepositories(basePackages = "org.ce.cc.core.repository")
public class NotifierApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotifierApplication.class, args);
    }
}
