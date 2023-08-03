package files;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/AddRecipeServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
                 maxFileSize = 1024 * 1024 * 10,      // 10MB
                 maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class AddRecipeServlet extends HttpServlet {
    private String dbURL = "jdbc:mysql://localhost:3306/your_database_name";
    private String dbUser = "your_database_username";
    private String dbPassword = "your_database_password";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String recipeName = request.getParameter("recipeName");
        String recipeContent = request.getParameter("recipeContent");
        Part recipeImagePart = request.getPart("recipeImage");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);

            String insertQuery = "INSERT INTO recipes (recipe_name, recipe_content, recipe_image) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);
            preparedStatement.setString(1, recipeName);
            preparedStatement.setString(2, recipeContent);
            
            // Save the recipe image file to a folder on the server and store the image file name in the database
            String recipeImageFileName = saveRecipeImage(recipeImagePart); 
            preparedStatement.setString(3, recipeImageFileName);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            conn.close();

            // Redirect the user to a success page or back to the home page
            response.sendRedirect("home.jsp?message=Recipe added successfully");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately (logging, error page, etc.).
        }
    }

    // Method to save the recipe image to a folder on the server and return the image file name
    private String saveRecipeImage(Part recipeImagePart) throws IOException {
        String recipeImageFileName = null;
        String uploadPath = getServletContext().getRealPath("") + File.separator + "images";

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        try (InputStream inputStream = recipeImagePart.getInputStream()) {
            recipeImageFileName = UUID.randomUUID().toString() + "_" + recipeImagePart.getSubmittedFileName();
            String filePath = uploadPath + File.separator + recipeImageFileName;
            Files.copy(inputStream, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        }

        return recipeImageFileName;
    }
}
