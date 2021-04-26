import java.sql.*;

class Main {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:/Users/sebastian/IdeaProjects/forsøg1eksamen/students.db";
        StudentModel SDB = new StudentModel(url);
        try {
            SDB.connect();
            SDB.CreateStatement();
            SDB.CourseInfoQuery();
            SDB.AverageCourseGradeQuery();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                SDB.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
