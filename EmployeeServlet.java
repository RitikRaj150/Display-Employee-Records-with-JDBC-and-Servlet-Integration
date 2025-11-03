import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class EmployeeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String empId = request.getParameter("empid");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/nimbusdb", "root", "password");

            Statement st = con.createStatement();
            ResultSet rs;

            if (empId != null && !empId.isEmpty()) {
                rs = st.executeQuery("SELECT * FROM Employee WHERE EmpID=" + empId);
                out.println("<h3>Employee Details:</h3>");
            } else {
                rs = st.executeQuery("SELECT * FROM Employee");
                out.println("<h3>All Employee Records:</h3>");
            }

            out.println("<table border='1'><tr><th>ID</th><th>Name</th><th>Salary</th></tr>");
            while (rs.next()) {
                out.println("<tr><td>" + rs.getInt(1) + "</td><td>" +
                    rs.getString(2) + "</td><td>" + rs.getDouble(3) + "</td></tr>");
            }
            out.println("</table>");

            con.close();
        } catch (Exception e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
        }

        out.close();
    }
}
