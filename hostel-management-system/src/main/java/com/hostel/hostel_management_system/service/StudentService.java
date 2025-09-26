package com.hostel.hostel_management_system.service;

import com.hostel.hostel_management_system.model.Student;
import com.hostel.hostel_management_system.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student saveStudent(Student student) {
        // ✅ Prevent duplicate email
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new RuntimeException("A student with this email already exists!");
        }

        // ✅ Validate floor range
        if (student.getBuilding().equalsIgnoreCase("girls") && (student.getFloor() < 0 || student.getFloor() > 20)) {
            throw new RuntimeException("Girls building floors must be between 0 and 20");
        }
        if (student.getBuilding().equalsIgnoreCase("boys") && (student.getFloor() < 20 || student.getFloor() > 40)) {
            throw new RuntimeException("Boys building floors must be between 20 and 40");
        }

        // ✅ Room capacity check (max 5 students per room)
        List<Student> studentsInRoom = studentRepository.findByBuildingAndFloorAndRoom(
                student.getBuilding(), student.getFloor(), student.getRoom()
        );
        if (studentsInRoom.size() >= 5) {
            throw new RuntimeException("Room is full! Maximum 5 students allowed per room.");
        }

        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Student> getStudentsByRoom(String building, int floor, int room) {
        return studentRepository.findByBuildingAndFloorAndRoom(building, floor, room);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
