package com.deshmukhamit.springbootmysql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = { "application.properties","mysql.application.properties" })
public class SpringbootMysqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootMysqlApplication.class, args);
	}

}
