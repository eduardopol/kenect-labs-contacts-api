package com.kenect;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@OpenAPIDefinition(
		info = @Info(
				title = "Contact API",
				version = "1.0",
				description = "API for getting contacts",
				license = @License(
						name = "Apache 2.0",
						url = "http://www.apache.org/licenses/LICENSE-2.0.html"
				)
		)
)
public class KenectLabsContactsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(KenectLabsContactsApiApplication.class, args);
	}

}
