package com.twospoons.camel.rest

import groovy.util.logging.Slf4j
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@Slf4j
@SpringBootApplication
public class Application {

    public static void main(String... args){
        def context = SpringApplication.run(Application, args)

        context.getBeanDefinitionNames().sort().collect{
            log.debug it
        }
    }
}
