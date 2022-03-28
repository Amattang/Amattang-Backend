package com.example.amattang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class AmattangApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmattangApplication.class, args);
    }

}
