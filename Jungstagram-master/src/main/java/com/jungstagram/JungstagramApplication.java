package com.jungstagram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class JungstagramApplication {

	public static void main(String[] args) {
		SpringApplication.run(JungstagramApplication.class, args);
	}

}
	