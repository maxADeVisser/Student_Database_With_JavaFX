import java.sql.*;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;

public class StudentModel {
    Connection conn = null;
    String url;
    Statement stmt = null;
    ResultSet rs = null;
    String selectedCourse = "";
    String selectedStudent = "";

    StudentModel(String url) { //constructor
        this.url = url;
    }
    public void connect() throws SQLException {
        conn = getConnection(url);
    }
    public void close() throws SQLException {
        if (conn != null)
            conn.close();
    }
    public void createStatement() throws SQLException {
        this.stmt = conn.createStatement();
    }

    // -- DIFFERENT QUERY METHODS:
    public String courseInfoQuery() {
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

    public String averageCourseGradeQuery() {
        double avg = 0;
        String returnString = "";
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
            returnString = "Average grade: \n" + avg;
            rs = null;
            return returnString;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return returnString;
        }
    }
    public String studentCoursesQuery() {
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
                System.out.println("No courses found for that student");
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
    public String averageStudentGradeQuery() {
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
                System.out.println("No grades found");
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

    public void addStudent(String ID, String surname, String lastname, String cityID){
        String command = "INSERT INTO Students (Student_ID, Surname, Lastname, City_ID)\n" +
                "VALUES ('"+ID+"', '"+surname+"', '"+lastname+"', '"+cityID+"')";
        try {
            stmt.executeUpdate(command);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeStudent(){ // SKAL LAVES FÆRDIG. VIRKER IKKE ENDNU
        String command = "DELETE FROM Students WHERE Surname = 'Max';";
        String command1 = "DELETE FROM Student_enrollments WHERE Student_ID = 'STUDENT-MDV';";
        try {
            stmt.executeUpdate(command);
            stmt.executeUpdate(command1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<String> queryGetStudentNames(){
        ArrayList<String> studentNames = new ArrayList<>();
        String query = "SELECT Surname FROM Students;";
        try {
            rs = stmt.executeQuery(query);
            if (rs == null)
                System.out.println("No Students found");
            while (rs != null && rs.next()) {
                studentNames.add(rs.getString(1));
            }
            rs = null;
            return studentNames;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return studentNames;
        }
    }
    public ArrayList<String> queryGetCourses() {
        ArrayList<String> courseNames = new ArrayList<>();
        String query = "SELECT Course_name FROM Courses";
        try {
            rs = stmt.executeQuery(query);
            if (rs == null)
                System.out.println("No Courses found");
            while (rs != null && rs.next()) {
                courseNames.add(rs.getString(1));
            }
            rs = null;
            return courseNames;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return courseNames;
        }
    }



}
