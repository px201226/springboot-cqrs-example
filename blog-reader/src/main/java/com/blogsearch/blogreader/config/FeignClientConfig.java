package com.blogsearch.blogreader.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.Locale;
import java.util.TimeZone;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;


@Configuration
public class FeignClientConfig implements Jackson2ObjectMapperBuilderCustomizer {


	@Bean
	public FeignFormatterRegistrar localDateFeignFormatterRegister() {
		return (registry) -> {
			DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
			registrar.setUseIsoFormat(true);
			registrar.registerFormatters(registry);
		};
	}


	public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
		jacksonObjectMapperBuilder
				.featuresToEnable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)
				.featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
				.timeZone(TimeZone.getDefault())
				.locale(Locale.getDefault())
				.modulesToInstall(new JavaTimeModule());
	}
}