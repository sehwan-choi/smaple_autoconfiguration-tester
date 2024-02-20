package com.api.starter;

import com.mylib.api.MyLibConfig;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StarterApplication {

	@Autowired
	MyLibConfig.ApiUtil apiUtil;

	@PostConstruct
	public void init() {
		System.out.println("apiUtil = " + apiUtil);
	}

	public static void main(String[] args) {
		SpringApplication.run(StarterApplication.class, args);
	}

}
