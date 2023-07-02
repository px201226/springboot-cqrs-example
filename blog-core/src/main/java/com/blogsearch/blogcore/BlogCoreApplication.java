package com.blogsearch.blogcore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class BlogCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogCoreApplication.class, args);
	}

}
