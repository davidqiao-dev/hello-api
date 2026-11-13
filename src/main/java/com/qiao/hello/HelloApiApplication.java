package com.qiao.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class HelloApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloApiApplication.class, args);
	}

}
