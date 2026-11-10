package com.Organise.AI_Bookshelf;

import com.Organise.AI_Bookshelf.dto.ToolResponseDto;
import com.Organise.AI_Bookshelf.service.ToolService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class AiBookshelfApplication {

	public static void main(String[] args) {

		System.out.println("DB URL: " +
				System.getenv("DB_URL"));

		System.out.println("DB USERNAME: " +
				System.getenv("DB_USERNAME"));

		System.out.println("DB PASSWORD EXISTS: " +
				(System.getenv("DB_PASSWORD") != null));

		SpringApplication.run(AiBookshelfApplication.class, args);
	}

}