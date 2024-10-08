package com.springboot.blog;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
		info=@Info(
				title="Springboot Blog REST Api",
				description="Springboot Blog REST Api Documentation",
				version="v1.0",
				contact=@Contact(
						name="yusuf",
						email="yusuf@gmail.com"
				)
				
		),
		//branch1 merging to main practise
		externalDocs=@ExternalDocumentation(
				description="spring boot blog app documentation",
				url="https://github.com/yusufansari2001/Blog-Application"
		)
)

public class SpringbootBlogRestApiApplication {
	
	@Bean
	public ModelMapper moelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
	}

}
