<!DOCTYPE html>
<html>
<head><title>Search Employee</title></head>
<body>
<form action="EmployeeServlet" method="post">
Enter Employee ID: <input type="text" name="empId">
<input type="submit" value="Search">
</form>
</body>
</html>
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class EmployeeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String empId = request.getParameter("empId");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/company", "root", "password");

            String query = "SELECT * FROM employees WHERE emp_id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, empId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                out.println("<h2>Employee Details</h2>");
                out.println("ID: " + rs.getInt(1) + "<br>");
                out.println("Name: " + rs.getString(2) + "<br>");
                out.println("Department: " + rs.getString(3));
            } else {
                out.println("No employee found with ID " + empId);
            }
            con.close();
        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
        }
    }
}
