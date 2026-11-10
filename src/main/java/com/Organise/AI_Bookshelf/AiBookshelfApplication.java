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

		SpringApplication.run(AiBookshelfApplication.class, args);
	}

}