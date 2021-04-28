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

    public void connect() throws SQLException { //connects to the url given in the main class
        conn = getConnection(url);
    }

    public void close() throws SQLException { //closes that connection
        if (conn != null)
            conn.close();
    }

    public void createStatement() throws SQLException {
        this.stmt = conn.createStatement(); // creates a Statement instance for sending SQL statements to the database we're connected to.
    }

    // ------- QUERY METHODS ------- //

    //Returns a string with every students surname, lastname and grade in the selected course separated by a nextLine
    public String courseInfoQuery() {
        String returnString = "";
        String queryCourse = "SELECT *" +
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

    //Returns the total average from the selected course
    public String averageCourseGradeQuery() {
        double avg = 0;
        String returnString = "";
        String queryCourse = "SELECT avg(grade)" +
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
            returnString = "Total average: " + avg;
            rs = null;
            return returnString;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return returnString;
        }
    }

    //Returns a string with the course names and grades for the selected student
    public String studentCoursesGradeQuery() {
        String returnString = "";
        String queryStudentGrade = "SELECT Course_name, Grade " +
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

    //returns a list with all the students names from the selected course
    public ArrayList<String> getStudentsInCourse() {
        ArrayList<String> returnList = new ArrayList<>();
        String query = "SELECT Surname " +
                "FROM Student_enrollments " +
                "         JOIN Students " +
                "              ON Student_enrollments.Student_ID = Students.Student_ID " +
                "         JOIN Courses " +
                "              ON Student_enrollments.Course_ID = Courses.Course_ID " +
                "WHERE Course_name = '"+selectedCourse+"';";
        try {
            rs = stmt.executeQuery(query);
            if (rs == null)
                System.out.println("No students found for that course");
            while (rs != null && rs.next()) {
                returnList.add(rs.getString("Surname"));
            }
            rs = null;
            return returnList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return returnList;
    }

    //Return a string with the total average grade for a student
    public String averageStudentGradeQuery() {
        double avg = 0;
        String returnString = "";
        String queryStudentGrade = "SELECT avg(grade)" +
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

    //return a grade for the selected student in the selected course
    public String studentCourseGradeQuery() {
        String returnString = "";
        String query = "SELECT Grades.Grade_ID " +
                "FROM Student_enrollments " +
                "   JOIN Grades " +
                "       ON Student_enrollments.Grade_ID = Grades.Grade_ID " +
                "   JOIN Students" +
                "        ON Student_enrollments.Student_ID = Students.Student_ID " +
                "   JOIN Courses" +
                "        ON Student_enrollments.Course_ID = Courses.Course_ID " +
                "WHERE Surname = '" + selectedStudent + "' " +
                "AND Course_name = '" + selectedCourse + "';";
        try {
            rs = stmt.executeQuery(query);
            if (rs == null) {
                System.out.println("No grade found for " + selectedStudent + " in course " + selectedCourse);
            } else {
                returnString += rs.getString("Grade_ID");
            }
            rs = null;
            return returnString;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return returnString;
    }

    //Returns the names of all the students in an array
    public ArrayList<String> queryGetStudentNames() {
        ArrayList<String> studentNames = new ArrayList<>();
        String query = "SELECT Surname FROM Students;";
        try {
            rs = stmt.executeQuery(query);
            if (rs == null)
                System.out.println("No Students found");
            while (rs != null && rs.next()) {
                studentNames.add(rs.getString("Surname"));

            }
            rs = null;
            return studentNames;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return studentNames;
        }
    }

    //Returns the names of all the courses in an array
    public ArrayList<String> queryGetCourses() {
        ArrayList<String> courseNames = new ArrayList<>();
        String query = "SELECT Course_name FROM Courses";
        try {
            rs = stmt.executeQuery(query);
            if (rs == null)
                System.out.println("No Courses found");
            while (rs != null && rs.next()) {
                courseNames.add(rs.getString("Course_name"));
            }
            rs = null;
            return courseNames;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return courseNames;
        }
    }

    ////Returns all the grades available
    public ArrayList<String> getAllGrades() {
        String query = "SELECT Grade_ID FROM Grades;";
        ArrayList<String> grades = new ArrayList<>();
        try {
            rs = stmt.executeQuery(query);
            if (rs == null)
                System.out.println("No grades found");
            while (rs != null && rs.next()) {
                grades.add(rs.getString("Grade_ID"));
            }
            rs = null;
            return grades;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return grades;
        }
    }

    //Update the grade from the selected student in the selected course.
    public void updateGrade(String grade){
        String command = "UPDATE Student_enrollments SET Grade_ID ='" + grade + "' " +
                "WHERE course_ID = (SELECT Course_ID FROM Courses WHERE Courses.Course_name = '" + selectedCourse + "') " +
                "AND Student_ID = (SELECT Student_ID FROM Students WHERE Students.Surname = '"+selectedStudent+"');";
        try {
            stmt.executeUpdate(command);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
