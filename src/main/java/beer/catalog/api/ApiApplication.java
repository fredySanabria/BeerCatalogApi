package beer.catalog.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@OpenAPIDefinition(
		info = @Info(
				title = "API for Manufacturers and Beers",
				version = "1.0",
				description = "API RESTful Documentation example using Hexagonal Architecture"
		)
)
@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}
}

