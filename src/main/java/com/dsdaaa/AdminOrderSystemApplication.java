package com.dsdaaa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dsdaaa.*.mapper")
public class AdminOrderSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminOrderSystemApplication.class, args);
	}

}
