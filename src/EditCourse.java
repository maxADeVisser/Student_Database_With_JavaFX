import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;

public class EditCourse {

    public static void display(StudentModel SDB) {

        int windowWidth = 400;
        int windowHeight = 300;

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //does so that the AlertBox must be dealt with when it pops up
        window.setTitle("Edit Course");
        window.setMinWidth(windowWidth);
        window.setMinHeight(windowHeight);


        try {
            SDB.connect();
            SDB.createStatement();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        // -- COMBOBOX FOR GRADES
        ComboBox<String> gradeCombobox = new ComboBox<>();
        gradeCombobox.setPromptText("Choose Grade");
        ArrayList<String> grades = SDB.getAllGrades();
        gradeCombobox.getItems().addAll(grades); //Adds the different studentNames to the combobox

        gradeCombobox.setOnAction(event -> { //UPDATING GRADE
            try{
                SDB.connect();
                SDB.createStatement();
                SDB.updateGrade(gradeCombobox.getValue());
                System.out.println("Grade updated");
                SDB.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        });

        //STUDENT CURRENT GRADE LABEL
        TextField currentGrade = new TextField();
        currentGrade.setMaxWidth(100);
        currentGrade.setEditable(false);

        Label currentGradeText = new Label("Current grade:");

        // -- LIST VIEW FOR STUDENTS
        ListView<String> studentList = new ListView<>();
        studentList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE); // can also be set to select multiple
        studentList.setMinWidth(200);
        ArrayList<String> studentNames = SDB.getStudentsInCourse();
        studentList.getItems().addAll(studentNames); //Adds the different studentNames

        studentList.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> { // adds a listener such that we can execute code when something is choosen in the list
            SDB.selectedStudent = studentList.getSelectionModel().getSelectedItem();
            try{
                SDB.connect();
                SDB.createStatement();
                currentGrade.setText(SDB.studentCourseGradeQuery());
                SDB.close();
            } catch (SQLException ex){
                System.out.println(ex.getMessage());
            }
        });

        VBox layout1 = new VBox(20);
        layout1.setPadding(new Insets(15, 12, 15, 12));
        layout1.setSpacing(10);
        layout1.getChildren().addAll(currentGradeText, currentGrade,gradeCombobox);


        HBox layout = new HBox(10);
        layout.setPadding(new Insets(15, 12, 15, 12));
        layout.setSpacing(10);
        layout.getChildren().addAll(studentList, layout1);
        layout.setAlignment(Pos.CENTER);


        try {
            SDB.close();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait(); //is seen alot in conjunction with line 12, since it implies that we need to handle the window before going back

    }
}


