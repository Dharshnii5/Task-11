import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/FeedbackServlet")
public class FeedbackServlet extends HttpServlet {

    // Database credentials
    private final String DB_URL = "jdbc:mysql://localhost:3306/feedbackdb"; // Update with your DB URL
    private final String DB_USER = "root";  // Update with your DB username
    private final String DB_PASSWORD = "";  // Update with your DB password

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Getting form data
        String name = request.getParameter("name");
        String bookName = request.getParameter("book_name");
        String feedback = request.getParameter("feedback");

        // Setting response type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // JDBC connection and insert data
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Step 1: Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Step 2: Establish the connection
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Step 3: Create SQL query
            String sql = "INSERT INTO feedback (name, book_name, feedback) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, bookName);
            stmt.setString(3, feedback);

            // Step 4: Execute the query
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                // Respond with a success message
                out.println("<h3>Feedback saved!</h3>");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            out.println("<h3>Error saving feedback!</h3>");
        } finally {
            // Step 5: Close the connection
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        // Close the PrintWriter
        out.close();
    }
}
