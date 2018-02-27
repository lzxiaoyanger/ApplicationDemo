package com.zz.personal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@SpringBootApplication
//@MapperScan("com.zz.personal.dao.mapper")
//@SpringBootApplication
@SpringBootApplication
public class PersonalApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalApplication.class, args);
	}

}
