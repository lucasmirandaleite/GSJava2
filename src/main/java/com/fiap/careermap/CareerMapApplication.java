package com.fiap.careermap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableCaching // Habilita o uso de cache
@EnableAsync // Habilita o uso de métodos assíncronos (para simular filas)
public class CareerMapApplication {

	public static void main(String[] args) {
		SpringApplication.run(CareerMapApplication.class, args);
	}

}
