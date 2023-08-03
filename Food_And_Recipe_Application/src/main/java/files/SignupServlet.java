package files;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String dbURL = "jdbc:mysql://localhost:3306/database1";
        String dbUser = "root";
        String dbPassword = "password";

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String emailId = request.getParameter("emailId");
        String phoneNumber = request.getParameter("mobileNumber");
        String password = request.getParameter("password");

        // Hash the password using BCrypt
        

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            System.out.println("Success");

            String insertQuery = "INSERT INTO users (first_name, last_name, email, phone_number, password) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, emailId);
            preparedStatement.setString(4, phoneNumber);
            preparedStatement.setString(5, password);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            conn.close();

            // Set an attribute to indicate success
            request.setAttribute("signupSuccess", true);

            // Forward the user to the success page
            request.getRequestDispatcher("success.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            // Redirect the user to an error page or show an error message to the user
            response.sendRedirect("error.jsp");
        }
    }
}

