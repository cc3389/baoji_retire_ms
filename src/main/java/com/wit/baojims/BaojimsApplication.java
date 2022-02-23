package com.wit.baojims;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@MapperScan("com.wit.baojims.mapper")
@SpringBootApplication
public class BaojimsApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaojimsApplication.class, args);
    }

}
