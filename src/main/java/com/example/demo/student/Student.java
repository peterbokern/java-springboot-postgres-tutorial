package com.example.demo.student;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;

// Marks this class as a JPA Entity, meaning Hibernate will map it to a table in the database.
// Each instance of the `Student` class will represent a row in the database table.
@Entity

// Maps this class to the `students` table in the database.
// If this annotation is not provided, Hibernate will default to using the class name as the table name.
@Table(name = "students")
public class Student {

    // Marks this field as the primary key of the database table.
    @Id

    // Defines a sequence generator for generating unique IDs for the primary key.
    // This generator is linked to a database sequence, which ensures IDs are unique and auto-incremented.
    @SequenceGenerator(
            name = "student_sequence",             // The name of the sequence generator in Hibernate
            sequenceName = "student_sequence",     // The actual name of the sequence in the database
            allocationSize = 1                     // Specifies that the sequence will increment by 1 for each new ID
    )

    // Specifies how the primary key should be generated.
    // `strategy = GenerationType.SEQUENCE` tells Hibernate to use a sequence for generating IDs.
    // `generator = "student_sequence"` links this field to the sequence generator defined above.
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private Long id; // Represents the unique identifier (primary key) for each student in the table.

    private String name; // Stores the name of the student.
    private String email; // Stores the email address of the student.
    private LocalDate dateOfBirth; // Stores the date of birth of the student.

    // The @Transient annotation tells Hibernate to ignore this field when persisting data to the database.
    // This means the `age` field will not be stored in the `students` table.
    // Instead, it is computed dynamically based on the `dateOfBirth`.
    @Transient
    private Integer age; // Stores the age of the student.

    // A no-argument (default) constructor is required by Hibernate and Spring Boot.
    // This constructor allows the frameworks to create objects of this class via reflection, especially when fetching data from the database.
    public Student() {
    }

    // Constructor to initialize all fields of the `Student` object, including the `id`.
    // Typically used when you have a pre-defined `id`, such as during testing or when fetching from external data sources.
    public Student(Long id, String name, String email, LocalDate dateOfBirth) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    // Constructor to initialize all fields except the `id`.
    // This is useful for creating new students where the ID will be auto-generated by the database.
    // Uses constructor chaining to call the full constructor, passing `null` for the `id`.
    public Student(String name, String email, LocalDate dateOfBirth) {
        this(null, name, email, dateOfBirth);
    }

    // Getter and setter for `id`. These allow controlled access and modification of the `id` field.
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter and setter for `name`.
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and setter for `email`.
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and setter for `dateOfBirth`.
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    // Getter for `age`. The age is calculated dynamically based on the `dateOfBirth` field.
    // This ensures the `age` is always up-to-date without being explicitly stored in the database.
    public Integer getAge() {
        return Period.between(this.dateOfBirth, LocalDate.now()).getYears();
    }
}

// Overrides the default `toString()` method of the `Object
