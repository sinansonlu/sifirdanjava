import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

class SQTest {
    public static void main(String[] args) {

        Connection conn = null;

        String url = "jdbc:sqlite:D:/TestDB/MyData.db";

        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");

            String sql = "SELECT Name, Surname, Scholarship FROM Student WHERE Scholarship > ? AND Scholarship < ?";

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, 400);
            stmt.setInt(2, 700);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println(
                        rs.getString(1)
                                + " " + rs.getString(2)
                                + " " + rs.getString(3));
            }

            stmt = conn.prepareStatement("SELECT MAX(ID) FROM Student");
            rs = stmt.executeQuery();

            int idNext = 0;

            if (rs.next()) {
                idNext = rs.getInt(1) + 1;
            }

            stmt = conn.prepareStatement("INSERT INTO Student(ID,Name,Surname,Scholarship) VALUES(?,?,?,?)");
            stmt.setInt(1, idNext);
            stmt.setString(2, "Abdik");
            stmt.setString(3, "Mabdik");
            stmt.setInt(4, 600);
            stmt.executeUpdate();

            stmt = conn.prepareStatement("UPDATE Student SET Scholarship = Scholarship + 50");
            stmt.executeUpdate();

            stmt = conn.prepareStatement("DELETE FROM Student WHERE Scholarship < 400");
            stmt.executeUpdate();

            stmt = conn.prepareStatement("SELECT * FROM Student");
            rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getString(2));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
            if (conn != null) {
                conn.close();
                System.out.println("Bağlantı sona erdi.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}