package com.example.demo.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

// The StudentService class handles the business logic of the application.
// By marking it with @Service, Spring Boot recognizes this class as a service component and manages it as a bean.
// This allows the service to be injected into other components like controllers.
@Service
public class StudentService {

    // Declares a dependency on the StudentRepository, which is used to interact with the database.
    // The 'final' keyword ensures the dependency is immutable once injected.
    private final StudentRepository studentRepository;

    // Constructor-based Dependency Injection:
    // Spring Boot automatically injects the StudentRepository bean into this service.
    @Autowired // Optional for a single constructor, but explicitly marks this as dependency injection.
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Retrieves a list of all students from the database using the repository.
    // The `findAll()` method is provided by JpaRepository, which the repository extends.
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    // Adds a new student to the database.
    // Before saving, it checks if the email already exists using a custom repository method.
    public void addNewStudent(Student student) {
        // Check if a student with the provided email already exists using Optional.
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());

        // If a student with the same email exists, throw an exception.
        if (studentOptional.isPresent()) {
            throw new IllegalStateException("Email already exists");
        }

        // If no email match is found, save the new student to the database.
        studentRepository.save(student);
    }

    // Deletes a student from the database based on the provided ID.
    public void deleteStudent(Long id) {
        // Check if a student with the given ID exists in the database.
        boolean exists = studentRepository.existsById(id);

        if (!exists) {
            // If the student doesn't exist, throw an exception.
            throw new IllegalStateException("Student with id " + id + " does not exist.");
        }

        // If the student exists, delete it by its ID.
        studentRepository.deleteById(id);
    }

    // Updates a student's details (name and/or email) in the database.
    // @Transactional ensures the operation is executed as part of a single transaction.
    // Changes to the student entity are automatically persisted at the end of the transaction.
    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        // Retrieve the student by ID. If the student doesn't exist, throw an exception.
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("Student with id " + studentId + " does not exist."));

        // Update the name if it's non-null, non-empty, and different from the existing name.
        if (name != null && !name.isEmpty() && !Objects.equals(name, student.getName())) {
            student.setName(name); // Updates the name field in the entity.
        }

        // Update the email if it's non-null, non-empty, and different from the existing email.
        if (email != null && !email.isEmpty() && !Objects.equals(email, student.getEmail())) {
            // Check if the email is already taken by another student.
            Optional<Student> studentByEmail = studentRepository.findStudentByEmail(email);
            if (studentByEmail.isPresent() && !studentByEmail.get().getId().equals(studentId)) {
                throw new IllegalStateException("Email already taken");
            }
            student.setEmail(email); // Updates the email field in the entity.
        }
    }
}
