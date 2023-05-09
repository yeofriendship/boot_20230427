package com.example.boot_20230427;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.controller"}) // 컨트롤러 위치 설정
public class Boot20230427Application {

	public static void main(String[] args) {
		SpringApplication.run(Boot20230427Application.class, args);
	}

}
