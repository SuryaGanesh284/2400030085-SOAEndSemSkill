package com.exam.tourist;

import com.exam.tourist.model.Tourist;
import com.exam.tourist.repository.TouristRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TouristApplication {

    public static void main(String[] args) {
        SpringApplication.run(TouristApplication.class, args);
    }

    // Pre-populates sample data on startup so PUT endpoint can be tested immediately
    @Bean
    CommandLineRunner initData(TouristRepository repository) {
        return args -> {
            repository.save(new Tourist("Alice Johnson", "alice@example.com", "Switzerland", 3200.0));
            repository.save(new Tourist("Bob Martin", "bob@example.com", "Japan", 2500.0));
            System.out.println(">> Sample tourist records seeded for testing!");
        };
    }
}
