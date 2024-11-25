
+++

# Comprehensive Tutorial: Building a Student Management System with Spring Boot and PostgreSQL

## **Introduction**

In this detailed tutorial, we will develop a **Student Management System** using **Spring Boot** and **PostgreSQL**. This guide provides step-by-step instructions along with a deeper understanding of how Spring Boot integrates with database technologies to simplify backend development.  The entire project was made following the comprehensive tutorial by Amigoscode. I created this tutorial as an easy step by step guide to setup the basics and use it as future reference. I'd recommend to follow the course:
https://www.youtube.com/watch?v=9SGDpanrc8U&t=629s

### **What You Will Learn**

-   How to set up a Spring Boot project using **Spring Initializr**.
-   Configuring PostgreSQL as the database.
-   Designing a data model with **JPA annotations**.
-   Implementing business logic in the **service layer**.
-   Building **RESTful APIs** to interact with student data.

----------

## **Step 1: Project Setup**

### **1.1 Initialize Project Using Spring Initializr**

1.  Open [Spring Initializr](https://start.spring.io).

2.  Configure the project:

    -   **Project Type**: Maven Project
    -   **Language**: Java
    -   **Spring Boot Version**: Latest stable release
    -   **Project Metadata**:
        -   **Group**: `com.example`
        -   **Artifact**: `demo`
    -   **Dependencies**:
        -   **Spring Web**
        -   **Spring Data JPA**
        -   **PostgreSQL Driver**
        -   **Lombok**
3.  Click **Generate** to download the project. Extract the zip file and open it in an IDE such as IntelliJ IDEA or Eclipse.


----------

### **1.2 Configure `application.properties`**

Edit `src/main/resources/application.properties` with the following:


	`spring.datasource.url=jdbc:postgresql://localhost:5433/students
	spring.datasource.username=postgres
	spring.datasource.password=secret
	spring.jpa.hibernate.ddl-auto=update
	spring.jpa.show-sql=true
	spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
	spring.jpa.properties.hibernate.format_sql=true` 

-   **`spring.datasource.url`**: URL to connect to PostgreSQL.
-   **`spring.jpa.hibernate.ddl-auto=update`**: Automatically updates the schema.
-   **`spring.jpa.show-sql`**: Enables SQL query logging.

----------

## **Step 2: Define the Data Model**

### **2.1 Create the `Student` Entity**

Create a new package `com.example.demo.student` and add the following class:



	`package com.example.demo.student;

	import jakarta.persistence.*;
	import lombok.Getter;
	import lombok.Setter;
	import lombok.NoArgsConstructor;
	import java.time.LocalDate;

	@Entity
	@Getter
	@Setter
	@NoArgsConstructor
	@Table(name = "students")
	public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_generator")
    @SequenceGenerator(name = "student_generator", sequenceName = "student_seq", allocationSize = 1)
    private Long id;

    private String name;
    private String email;
    private LocalDate dateOfBirth;

    public Student(String name, String email, LocalDate dateOfBirth) {
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }
}`

#### **Explanation**

-   **`@Entity`**: Marks the class as a JPA entity.
-   **`@Table`**: Maps the entity to the `students` table.
-   **`@Id`**: Specifies the primary key.
-   **`@SequenceGenerator`**: Configures sequence-based ID generation.
-   **Lombok Annotations**: Reduces boilerplate with `@Getter`, `@Setter`, and `@NoArgsConstructor`.

----------

## **Step 3: Implement the Repository Layer**

Create a repository interface in `com.example.demo.student`:


	`package com.example.demo.student;

	import org.springframework.data.jpa.repository.JpaRepository;
	import org.springframework.stereotype.Repository;

	@Repository
	public interface StudentRepository extends JpaRepository<Student, Long> {
	}` 

-   **`JpaRepository`**: Provides CRUD operations out of the box.

----------

## **Step 4: Develop the Service Layer**

Add a service class in `com.example.demo.student`:


	`package com.example.demo.student;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Service;
	import java.util.List;

	@Service
	public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
            .orElseThrow(() -> new IllegalStateException("Student not found"));
    }

    public Student updateStudent(Long id, Student studentDetails) {
        Student student = getStudentById(id);
        student.setName(studentDetails.getName());
        student.setEmail(studentDetails.getEmail());
        student.setDateOfBirth(studentDetails.getDateOfBirth());
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
	}` 

----------

## **Step 5: Build the REST Controller**

Create a REST controller in `com.example.demo.student`:



	`package com.example.demo.student;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.*;

	import java.util.List;

	@RestController
	@RequestMapping("/api/students")
	public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.addStudent(student));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        return ResponseEntity.ok(studentService.updateStudent(id, studentDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
	}` 

#### **Key Annotations**

-   **`@RestController`**: Indicates a REST controller.
-   **`@RequestMapping`**: Maps base URL for all APIs in the controller.

----------

## **Conclusion**

You have successfully built a **Student Management System** with **Spring Boot** and **PostgreSQL**. This project demonstrates:

-   Setting up a Spring Boot project.
-   Using JPA for database interaction.
-   Building and testing RESTful APIs.

By leveraging **Spring Boot's auto-configuration** and **Spring Data JPA's abstraction**, you can focus on building business logic rather than handling boilerplate code. +++