import java.sql.*;

class Main {
    public static void main(String[] args) {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:/Users/sebastian/IdeaProjects/forsøg1eksamen/students.db";
            conn = DriverManager.getConnection(url);

            Statement stmt = null;
            String selectedCourse = "Essential Computing 1 E2019";
            double avg = 0;

            String query = "SELECT *" +          //We find everything in the Students and courses tables. We then joins the student IDs and the course IDS with their respective tables from the Student_enrollment table.
                    "FROM Student_enrollments " +
                        "JOIN Grades " +
                            "ON Student_enrollments.Grade_ID = Grades.Grade_ID " +
                        "JOIN Students " +
                            "ON Student_enrollments.Student_ID = Students.Student_ID " +
                        "JOIN Courses " +
                            "ON Student_enrollments.Course_ID = Courses.Course_ID " +
                    "WHERE Course_name = '" +selectedCourse+ "';";

            try {
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                System.out.println("Students that has " + selectedCourse + ": ");
                while (rs.next()) {
                    String surname = rs.getString("Surname");
                    String lastname = rs.getString("Lastname");
                    int grade = rs.getInt("Grade");
                    avg = rs.getDouble("avg(Grade)");
                    System.out.println(surname + " " + lastname + " - Grade: " + grade);
                }
                //System.out.println("Average Grade: " + avg);

            } catch (SQLException e) {
                throw new Error("Problem", e);
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        } catch (SQLException e) {
            throw new Error("Problem", e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
