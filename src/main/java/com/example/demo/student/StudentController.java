package com.example.demo.student;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// The @RestController annotation marks this class as a REST API controller.
// This means it can handle HTTP requests and automatically return responses in JSON format.
@RestController
@RequestMapping(path = "api/v1/students")
// Sets the base URL for all endpoints in this controller to http://localhost:8080/api/v1/students
public class StudentController {

    // The 'final' keyword ensures that this variable is immutable once initialized in the constructor.
    // This guarantees that the injected StudentService instance cannot be reassigned, promoting better design.
    private final StudentService studentService;

    // Constructor-based Dependency Injection:
    // This constructor tells Spring Boot that the StudentController depends on a StudentService.
    // Spring will automatically inject (provide) an instance of StudentService when creating the StudentController.
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // The @GetMapping annotation maps HTTP GET requests to this method.
    // Visiting http://localhost:8080/api/v1/students will trigger this method.
    // It fetches the list of students from the StudentService and returns it as a JSON response.
    @GetMapping
    public ResponseEntity<List<Student>> getStudents() {
        // Calls the getStudents() method from the StudentService class to fetch the data.
        // Returns the list of Student objects wrapped in a ResponseEntity with an HTTP 200 status.
        List<Student> students = studentService.getStudents();
        return ResponseEntity.ok(students); // HTTP 200 OK
    }

    // The @PostMapping annotation maps HTTP POST requests to this method.
    // This endpoint is triggered when a POST request is sent to http://localhost:8080/api/v1/students.
    // The @RequestBody annotation tells Spring to map the incoming JSON request body to a Student object.
    @PostMapping
    public ResponseEntity<String> registerNewStudent(@RequestBody Student student) {
        try {
            // Calls the addNewStudent() method from the StudentService to add the new student to the database.
            studentService.addNewStudent(student);
            return ResponseEntity.status(HttpStatus.CREATED) // HTTP 201 Created
                    .body("Student registered successfully");
        } catch (IllegalStateException e) {
            // Returns a 400 Bad Request if the email already exists or other business logic fails.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // The @DeleteMapping annotation maps HTTP DELETE requests to this method.
    // This endpoint is triggered when a DELETE request is sent to http://localhost:8080/api/v1/students/{studentId}.
    // The @PathVariable annotation extracts the {studentId} from the URL and maps it to the method parameter `id`.
    @DeleteMapping(path = "{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable("studentId") Long id) {
        try {
            // Calls the deleteStudent() method from the StudentService to delete the student by ID.
            studentService.deleteStudent(id);
            return ResponseEntity.ok("Student deleted successfully"); // HTTP 200 OK
        } catch (IllegalStateException e) {
            // Returns a 404 Not Found if the student does not exist.
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // The @PutMapping annotation maps HTTP PUT requests to this method.
    // This endpoint is triggered when a PUT request is sent to http://localhost:8080/api/v1/students/{studentId}.
    // It is used to update an existing student's details.
    // The @PathVariable annotation extracts the {studentId} from the URL and maps it to the `studentId` parameter.
    // The @RequestBody annotation maps the JSON body of the request to a Student object.
    @PutMapping(path = "{studentId}")
    public ResponseEntity<String> updateStudent(
            @PathVariable("studentId") Long studentId,
            @RequestBody Student updatedStudent) {
        try {
            // Calls the updateStudent() method from the StudentService to update the student's name and email.
            // Only fields provided in the JSON body (e.g., name, email) will be updated.
            studentService.updateStudent(studentId, updatedStudent.getName(), updatedStudent.getEmail());
            return ResponseEntity.ok("Student updated successfully"); // HTTP 200 OK
        } catch (IllegalStateException e) {
            // Returns a 400 Bad Request if the update fails (e.g., email conflict).
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
