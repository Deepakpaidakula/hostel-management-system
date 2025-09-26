package com.hostel.hostel_management_system.repository;

import com.hostel.hostel_management_system.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByBuildingAndFloorAndRoom(String building, int floor, int room);
    boolean existsByEmail(String email); // check duplicate email
}
