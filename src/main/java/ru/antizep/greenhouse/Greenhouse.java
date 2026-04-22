package ru.antizep.greenhouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // Понадобится для опроса Arduino по расписанию
public class Greenhouse {

	public static void main(String[] args) {
		SpringApplication.run(Greenhouse.class, args);
	}

}
