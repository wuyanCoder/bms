package com.wuyan.bms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * BmsApplication
 *
 * @author {yuanwei}
 * @date 2020/2/11 22:31
 */
@SpringBootApplication
@MapperScan("com.wuyan.bms.mapper")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class BmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(BmsApplication.class,args);
    }
}
