/*
package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

// Marks this class as a configuration class where beans are defined.
@Configuration
public class StudentConfig {

    // Defines a CommandLineRunner bean, which executes when the application starts.
    // This is typically used for initializing data or performing setup tasks.
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            // Create new Student objects.
            Student maki = new Student(
                    "Maki",                     // Name
                    "Maki@hotamil.com",         // Email
                    LocalDate.of(2000, Month.JANUARY, 5)// Date of Birth

            );

            Student pita = new Student(
                    "Pita",                     // Name
                    "Pita@hotamil.com",         // Email
                    LocalDate.of(2000, Month.MARCH, 5)//dateofbirth
            );

            // Save the students to the database.
            repository.saveAll(
                    List.of(pita, maki)
            );
        };
    }
}
*/
