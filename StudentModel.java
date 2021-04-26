import java.sql.*;

import static java.sql.DriverManager.getConnection;

public class StudentModel {
    Connection conn = null;
    String url;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String selectedCourse = "Essential Computing 1 E2019";

    StudentModel(String url) {
        this.url = url;
    }

    public void connect() throws SQLException {
        conn = getConnection(url);
    }

    public void close() throws SQLException {
        if (conn != null)
            conn.close();
    }

    public void CreateStatement() throws SQLException {
        this.stmt = conn.createStatement();
    }

    public void CourseInfoQuery() {
        String queryCourse = "SELECT *" +          //We find everything in the Students and courses tables. We then joins the student IDs and the course IDS with their respective tables from the Student_enrollment table.
                "FROM Student_enrollments " +
                "JOIN Grades " +
                "ON Student_enrollments.Grade_ID = Grades.Grade_ID " +
                "JOIN Students " +
                "ON Student_enrollments.Student_ID = Students.Student_ID " +
                "JOIN Courses " +
                "ON Student_enrollments.Course_ID = Courses.Course_ID " +
                "WHERE Course_name = '" + selectedCourse + "';";
        try {
            rs = stmt.executeQuery(queryCourse);
            if (rs == null)
                System.out.println("No Student with that name");
            while (rs != null && rs.next()) {
                String surname = rs.getString("surname");
                String lastname = rs.getString("lastname");
                int grade = rs.getInt("Grade");
                System.out.println(surname + " " + lastname + " - Grade: " + grade);
            }
            rs = null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void AverageCourseGradeQuery() {
        double avg = 0;
        String queryCourse = "SELECT avg(grade)" +          //We find everything in the Students and courses tables. We then joins the student IDs and the course IDS with their respective tables from the Student_enrollment table.
                "FROM Student_enrollments " +
                "JOIN Grades " +
                "ON Student_enrollments.Grade_ID = Grades.Grade_ID " +
                "JOIN Courses " +
                "ON Student_enrollments.Course_ID = Courses.Course_ID " +
                "WHERE Course_name = '" + selectedCourse + "';";
        try {
            rs = stmt.executeQuery(queryCourse);
            if (rs == null)
                System.out.println("No Student with that name");
            while (rs != null && rs.next()) {
                avg = rs.getDouble("avg(grade)");
            }
            System.out.println("Average grade for " + selectedCourse + ": " + avg);
            rs = null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
