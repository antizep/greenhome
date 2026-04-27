package ru.antizep.greenhouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Greenhouse { 
	public static void main(String[] args) {
		SpringApplication.run(Greenhouse.class, args);
	}

}
