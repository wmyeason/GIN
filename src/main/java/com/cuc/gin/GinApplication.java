package com.cuc.gin;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.cuc.gin.mapper")
@EnableScheduling
public class GinApplication {

	public static void main(String[] args) {
		SpringApplication.run(GinApplication.class, args);
		System.out.println("项目启动成功！");
	}

	@Bean
	public ObjectMapper objectMapper(){
		return new ObjectMapper();
	}

}
