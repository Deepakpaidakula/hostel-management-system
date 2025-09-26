package com.hostel.hostel_management_system.controller;

import com.hostel.hostel_management_system.model.Student;
import com.hostel.hostel_management_system.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:3000") // for React frontend
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // ✅ Add student
    @PostMapping
    public Object addStudent(@RequestBody Student student) {
        try {
            return studentService.saveStudent(student);
        } catch (RuntimeException e) {
            return Map.of("error", e.getMessage());
        }
    }

    // ✅ Fetch all students
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    // ✅ Fetch students by room
    @GetMapping("/{building}/{floor}/{room}")
    public List<Student> getStudents(
            @PathVariable String building,
            @PathVariable int floor,
            @PathVariable int room) {
        return studentService.getStudentsByRoom(building, floor, room);
    }

    // ✅ Delete student
    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "Student removed successfully!";
    }
}
