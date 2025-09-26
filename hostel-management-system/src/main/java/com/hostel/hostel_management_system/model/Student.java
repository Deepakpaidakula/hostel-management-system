package com.hostel.hostel_management_system.model;

import jakarta.persistence.*; 
import jakarta.validation.constraints.NotBlank; 
import jakarta.validation.constraints.Email; 
import jakarta.validation.constraints.Pattern; 
import jakarta.validation.constraints.Min; 
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.AssertTrue;

@Entity
@Table(
    name = "students",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})}
)
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email")
    @Pattern(
        regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.com$",
        message = "Email must end with .com"
    )
    private String email;

    @NotBlank(message = "Phone cannot be empty")
    @Pattern(
        regexp = "^(\\+91[1-9][0-9]{9}|[1-9][0-9]{9}|\\+[1-9][0-9]{7,14})$",
        message = "Invalid phone number (must not start with 00)"
    )
    private String phone;

    @Min(value = 20, message = "Age must be at least 20")
    @Max(value = 30, message = "Age must not exceed 30")
    private int age;

    @Pattern(
        regexp = "ECE|CSE|MECH|CIVIL|EEE",
        message = "Course must be ECE, CSE, MECH, CIVIL, or EEE"
    )
    private String course;

    @Pattern(
        regexp = "boys|girls",
        message = "Building must be either 'boys' or 'girls'"
    )
    private String building;

    @Min(value = 1, message = "Floor number must be valid")
    private int floor;

    @Min(value = 1, message = "Room number must be valid")
    private int room;

    @NotBlank(message = "Gender cannot be empty")
    private String gender;

    // ✅ Custom Floor Validation
    @AssertTrue(message = "Boys' building floors must be between 20 and 40")
    private boolean isValidFloor() {
        if ("boys".equalsIgnoreCase(building)) {
            return floor >= 20 && floor <= 40;
        }
        return true; // girls can have any floor
    }

    // ✅ Custom Room Validation
    @AssertTrue(message = "Invalid room number for gender")
    private boolean isValidRoom() {
        if ("Male".equalsIgnoreCase(gender) || "boys".equalsIgnoreCase(building)) {
            return room >= 100;  // Boys start from 100
        } else if ("Female".equalsIgnoreCase(gender) || "girls".equalsIgnoreCase(building)) {
            return room >= 1 && room <= 100; // Girls 1–100
        }
        return true;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }

    public String getBuilding() { return building; }
    public void setBuilding(String building) { this.building = building; }

    public int getFloor() { return floor; }
    public void setFloor(int floor) { this.floor = floor; }

    public int getRoom() { return room; }
    public void setRoom(int room) { this.room = room; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
}
