<%@ page language="java" %>
<html>
<head><title>Student Attendance</title></head>
<body>
<h2>Mark Attendance</h2>
<form action="AttendanceServlet" method="post">
Roll No: <input type="text" name="roll"><br>
Name: <input type="text" name="name"><br>
Status: 
<select name="status">
    <option>Present</option>
    <option>Absent</option>
</select><br>
<input type="submit" value="Submit">
</form>
</body>
</html>
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class AttendanceServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String roll = request.getParameter("roll");
        String name = request.getParameter("name");
        String status = request.getParameter("status");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/student_portal", "root", "password");

            String query = "INSERT INTO attendance (roll_no, name, status) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, roll);
            ps.setString(2, name);
            ps.setString(3, status);

            int i = ps.executeUpdate();
            if (i > 0) {
                out.println("<h3>Attendance marked successfully for " + name + "!</h3>");
            }
            con.close();
        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
        }
    }
}
CREATE TABLE attendance (
    id INT AUTO_INCREMENT PRIMARY KEY,
    roll_no VARCHAR(20),
    name VARCHAR(100),
    status VARCHAR(10)
);
