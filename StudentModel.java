import java.sql.*;

import static java.sql.DriverManager.getConnection;

public class StudentModel {
    Connection conn = null;
    String url;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String selectedCourse = "";
    String selectedStudent = "";

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

    public String CourseInfoQuery() {
        String returnString = "";
        String queryCourse = "SELECT *" + //We find everything in the Students and courses tables. We then joins the student IDs and the course IDS with their respective tables from the Student_enrollment table.
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
                returnString += surname + " " + lastname + " - Grade: " + grade + "\n";
            }
            rs = null;
            return returnString;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return returnString;
    }


    public String AverageCourseGradeQuery() {
        double avg = 0;
        String returnString = "";
        String queryCourse = "SELECT avg(grade)" +          //We find everything in the Students and courses tables. We then join the student IDs and the course IDS with their respective tables from the Student_enrollment table.
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
            returnString = "Average grade: \n" + avg;
            rs = null;
            return returnString;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return returnString;
        }
    }

    public String AverageStudentGradeQuery() {
        double avg = 0;
        String returnString = "";
        String queryStudentGrade = "SELECT avg(grade)" +          //We find everything in the Students and courses tables. We then joins the student IDs and the course IDS with their respective tables from the Student_enrollment table.
                "FROM Student_enrollments " +
                "JOIN Grades " +
                "ON Student_enrollments.Grade_ID = Grades.Grade_ID " +
                "JOIN Students " +
                "ON Student_enrollments.Student_ID = Students.Student_ID " +
                "WHERE Surname = '" + selectedStudent + "'" +
                "or Lastname = '" + selectedStudent + "';";
        try {
            rs = stmt.executeQuery(queryStudentGrade);
            if (rs == null)
                System.out.println("No Student with that name");
            while (rs != null && rs.next()) {
                avg = rs.getDouble("avg(grade)");
            }
            returnString = "Total Average: " + avg;
            rs = null;
            return returnString;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return returnString;
        }
    }

    public String StudentCoursesQuery() {
        String returnString = "";
        String queryStudentGrade = "SELECT Course_name, Grade " +          //We find everything in the Students and courses tables. We then joins the student IDs and the course IDS with their respective tables from the Student_enrollment table.
                "FROM Student_enrollments " +
                "JOIN Grades " +
                "ON Student_enrollments.Grade_ID = Grades.Grade_ID " +
                "JOIN Students " +
                "ON Student_enrollments.Student_ID = Students.Student_ID " +
                "JOIN Courses " +
                "ON Student_enrollments.Course_ID = Courses.Course_ID " +
                "WHERE Surname = '" + selectedStudent + "'" +
                "or Lastname = '" + selectedStudent + "';";
        try {
            rs = stmt.executeQuery(queryStudentGrade);
            if (rs == null)
                System.out.println("No Student with that name");
            while (rs != null && rs.next()) {
                String course_name = rs.getString("Course_name");
                int grade = rs.getInt("Grade");
                returnString += course_name + " - Grade: " + grade + "\n";
            }
            rs = null;
            return returnString;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return returnString;
    }
}
