import React, { useState, useEffect } from "react";
import axios from "axios";
import "./App.css";



function App() {
  const [students, setStudents] = useState([]);
  const [form, setForm] = useState({
    name: "",
    email: "",
    phone: "",
    age: "",
    course: "ECE",
    building: "boys",
    floor: "",
    room: "",
    gender: "Male",
  });
  const [errors, setErrors] = useState({});

  // Fetch students from backend
  useEffect(() => {
    fetchStudents();
  }, []);

  const fetchStudents = async () => {
    try {
      const res = await axios.get("http://localhost:8080/api/students");
      setStudents(res.data);
    } catch (err) {
      console.error("Error fetching students:", err);
    }
  };

  // Handle input changes
  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  // Frontend validation
  const validate = () => {
    let tempErrors = {};
    if (!form.name.trim()) tempErrors.name = "Name cannot be empty";
    if (!form.email.endsWith("@gmail.com")) tempErrors.email = "Email must end with @gmail.com";
    if (!/^[1-9][0-9]{9}$/.test(form.phone)) tempErrors.phone = "Phone must be 10 digits and not start with 0";
    if (form.building === "boys") {
      const f = parseInt(form.floor);
      if (isNaN(f) || f < 20 || f > 40) tempErrors.floor = "Boys floor must be between 20 and 40";
    }
    if (!form.gender) tempErrors.gender = "Gender is required";
    setErrors(tempErrors);
    return Object.keys(tempErrors).length === 0;
  };

  // Submit form
  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validate()) return;

    try {
      await axios.post("http://localhost:8080/api/students", form);
      fetchStudents();
      setForm({
        name: "",
        email: "",
        phone: "",
        age: "",
        course: "ECE",
        building: "boys",
        floor: "",
        room: "",
        gender: "Male",
      });
      setErrors({});
    } catch (err) {
      console.error("Error adding student:", err);
      alert("Error adding student. Check backend connection.");
    }
  };

  const deleteStudent = async (id) => {
    try {
      await axios.delete(`http://localhost:8080/api/students/${id}`);
      fetchStudents();
    } catch (err) {
      console.error("Error deleting student:", err);
    }
  };

  // Floor options based on building
  const floorOptions = () => {
    let floors = [];
    if (form.building === "boys") {
      for (let i = 20; i <= 40; i++) floors.push(i);
    } else {
      for (let i = 1; i <= 20; i++) floors.push(i);
    }
    return floors;
  };

  // Room options based on gender
  const roomOptions = () => {
    let rooms = [];
    if (form.gender === "Male") {
      for (let i = 100; i <= 199; i++) rooms.push(i);
    } else {
      for (let i = 200; i <= 299; i++) rooms.push(i);
    }
    return rooms;
  };

  return (
    <div className="app-container">
      <h1>🏠 Hostel Management System</h1>
      <form className="form-box" onSubmit={handleSubmit}>
        <label>
          Name:
          <input type="text" name="name" value={form.name} onChange={handleChange} />
          {errors.name && <span className="error">{errors.name}</span>}
        </label>

        <label>
          Email:
          <input type="email" name="email" value={form.email} onChange={handleChange} />
          {errors.email && <span className="error">{errors.email}</span>}
        </label>

        <label>
          Phone:
          <input type="text" name="phone" value={form.phone} onChange={handleChange} />
          {errors.phone && <span className="error">{errors.phone}</span>}
        </label>

        <label>
          Age:
          <input type="number" name="age" value={form.age} onChange={handleChange} min="20" max="30" />
        </label>

        <label>
          Course:
          <select name="course" value={form.course} onChange={handleChange}>
            <option value="ECE">ECE</option>
            <option value="CSE">CSE</option>
            <option value="ME">ME</option>
          </select>
        </label>

        <label>
          Building:
          <select name="building" value={form.building} onChange={handleChange}>
            <option value="boys">Boys</option>
            <option value="girls">Girls</option>
          </select>
        </label>

        <label>
          Floor:
          <select name="floor" value={form.floor} onChange={handleChange}>
            <option value="">Select Floor</option>
            {floorOptions().map((f) => (
              <option key={f} value={f}>{f}</option>
            ))}
          </select>
          {errors.floor && <span className="error">{errors.floor}</span>}
        </label>

        <label>
          Room:
          <select name="room" value={form.room} onChange={handleChange}>
            <option value="">Select Room</option>
            {roomOptions().map((r) => (
              <option key={r} value={r}>{r}</option>
            ))}
          </select>
        </label>

        <label>
          Gender:
          <select name="gender" value={form.gender} onChange={handleChange}>
            <option value="Male">Male</option>
            <option value="Female">Female</option>
          </select>
          {errors.gender && <span className="error">{errors.gender}</span>}
        </label>

        <button type="submit" className="submit-btn">Add Student</button>
      </form>

      <h2>Student List</h2>
      <table className="student-table">
        <thead>
          <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Age</th>
            <th>Course</th>
            <th>Building</th>
            <th>Floor</th>
            <th>Room</th>
            <th>Gender</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {students.map((s) => (
            <tr key={s.id}>
              <td>{s.name}</td>
              <td>{s.email}</td>
              <td>{s.phone}</td>
              <td>{s.age}</td>
              <td>{s.course}</td>
              <td>{s.building}</td>
              <td>{s.floor}</td>
              <td>{s.room}</td>
              <td>{s.gender}</td>
              <td><button onClick={() => deleteStudent(s.id)}>Delete</button></td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default App;
