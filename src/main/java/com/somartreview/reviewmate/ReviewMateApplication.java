package com.somartreview.reviewmate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ReviewMateApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReviewMateApplication.class, args);
	}

}
