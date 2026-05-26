package com.barcafe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.barcafe")
public class BarcafeApplication {
    public static void main(String[] args){
        SpringApplication.run(BarcafeApplication.class, args);
    }
}
