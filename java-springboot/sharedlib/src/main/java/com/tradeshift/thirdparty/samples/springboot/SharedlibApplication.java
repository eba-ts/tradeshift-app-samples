package com.tradeshift.thirdparty.samples.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("com.tradeshift.thirdparty.samples.springboot")
public class SharedlibApplication {

	public static void main(String[] args) {
		SpringApplication.run(SharedlibApplication.class, args);
	}
}
