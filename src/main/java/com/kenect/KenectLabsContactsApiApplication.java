package com.kenect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class KenectLabsContactsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(KenectLabsContactsApiApplication.class, args);
	}

}
