package com.example.demo.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Marks this interface as a Spring-managed repository component.
// Spring automatically detects this during startup and provides an implementation at runtime.
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    /*
     * This interface defines the contract for database operations on the Student entity.
     * By extending JpaRepository, it inherits common CRUD methods like:
     * - save(entity): Saves a Student to the database.
     * - findById(id): Retrieves a Student by its ID.
     * - findAll(): Fetches all Students from the database.
     * - deleteById(id): Deletes a Student by its ID.
     *
     * Spring dynamically provides the implementation for these methods using the JpaRepository interface.
     * - No manual implementation is needed because Spring generates a proxy class at runtime.
     * - This proxy handles database interactions via Hibernate, ensuring flexibility and reducing boilerplate code.
     */

    /*
     * Custom query to find a Student by email.
     *
     * - @Query: This annotation allows defining custom JPQL (Java Persistence Query Language) queries.
     *   JPQL operates on Java objects (entities) and their fields, not directly on database tables or columns.
     * - "SELECT s FROM Student s WHERE s.email = ?1":
     *   This JPQL query selects a Student object (`s`) from the `Student` entity
     *   where the `email` field matches the given parameter (?1).
     * - Optional<Student>: The result is wrapped in an Optional to handle cases where no matching Student is found.
     *
     * Why This Works:
     * - The @Table annotation in the Student class maps the entity to the "students" database table.
     *   This tells Hibernate how to translate JPQL queries into SQL queries that interact with the database.
     * - At runtime, Spring Data JPA uses Hibernate to convert the JPQL query into a native SQL query:
     *   Example: "SELECT * FROM students WHERE email = ?" (if `@Table(name = "students")` is used).
     */
    @Query("SELECT s FROM Student s WHERE s.email = ?1")
    Optional<Student> findStudentByEmail(String email);
}
