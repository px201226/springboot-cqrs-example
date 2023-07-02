package com.blogsearch.blogreader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class BlogReaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogReaderApplication.class, args);
	}

}
