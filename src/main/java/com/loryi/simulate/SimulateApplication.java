package com.loryi.simulate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan(basePackages="com.zrar.easyweb.simulate.filter")
public class SimulateApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimulateApplication.class, args);
    }

}
