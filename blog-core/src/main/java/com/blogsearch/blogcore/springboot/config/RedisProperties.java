package com.blogsearch.blogcore.springboot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter @Setter
@Component
@ConfigurationProperties("spring.redis")
public class RedisProperties {

	private String host;
	private Integer port;

}
