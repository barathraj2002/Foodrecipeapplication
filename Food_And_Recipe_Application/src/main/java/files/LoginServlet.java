package files;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String dbURL = "jdbc:mysql://localhost:3306/database1";
        String dbUser = "root";
        String dbPassword = "password";

        String email =  request.getParameter("username");
        String enteredpassword = request.getParameter("password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);

            String selectQuery = "SELECT password FROM users WHERE email = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(selectQuery);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String Storedpassword = resultSet.getString("password");
                if (enteredpassword.equals(Storedpassword)) {
                    // Password is correct. User is authenticated.
                    // You can store the user's information in the session for use in the home page.
                    // Redirect the user to the home page after successful login.
                	HttpSession session = request.getSession();
                    session.setAttribute("loggedInUserEmail",email);
                    response.sendRedirect("home.jsp");
                } else {
                    // Password is incorrect. Show error message to the user.
                    response.sendRedirect("index.jsp?error=invalid credentials");
                }
            } else {
                // User with the entered email does not exist. Show error message to the user.
                response.sendRedirect("index.jsp?error=notfound");
            }

            resultSet.close();
            preparedStatement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            // Redirect the user to an error page or show an error message to the user
            response.sendRedirect("error.jsp");
        }
    }
}
