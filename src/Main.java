import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;

public class Main extends Application { //Application is the class holding all the javaFX stuff. So we always need to inherit it to use javaFX. futhermore everytime we want to handle a user input, we implement the EventHandler

    Stage window;
    Scene studentScene, courseScene, mainScene;

    int windowWidth = 800;
    int windowHeight = 500;


    public static void main(String[] args) {
        launch(args); //a method inside the Application class that launches javaFX application
    }


    @Override
    public void start(Stage primaryStage) throws Exception { //all the code inside here is the main javaFX code

        String url = "jdbc:sqlite:/Users/maxvisser/Documents/RUC/Datalogi/SD/Portfolie 3/Student_database/Students.db";
        StudentModel SDB = new StudentModel(url);

        window = primaryStage; //renaming for easier understanding of code
        window.setTitle("Student Database System"); //sets the title of the window

        // -- TEXT-AREA STUDENTS
        TextArea information = new TextArea();
        information.setEditable(false); // does so that TextArea is not edible
        information.setMaxWidth(windowWidth / 2);
        information.setPromptText("Information will be shown here");

        //STUDENT TOP MENU
        HBox studentTopMenu = new HBox(20); // top menu in studentspanel
        studentTopMenu.setPadding(new Insets(15, 12, 15, 12));
        studentTopMenu.setSpacing(10); //spacing between buttons
        studentTopMenu.setStyle("-fx-background-color: #336699;"); //color
        TextField searchStudent = new TextField();
        searchStudent.setPromptText("Search for a student");
        Button btnSearchButton = new Button("Search");
        btnSearchButton.setPrefSize(100, 20); //set the size of the button
        Button btnBackToMenu = new Button("Back to menu");
        btnBackToMenu.setPrefSize(100, 20); //set the size of the button
        studentTopMenu.getChildren().addAll(searchStudent, btnSearchButton, btnBackToMenu);

        // -- LIST VIEW STUDENTS
        ListView<String> studentListView = new ListView<>(); // specify what type the list is holding. This one holds strings
        studentListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE); // can also be set to select multiple
        studentListView.setMinWidth(windowWidth / 3);
        try {
            SDB.connect();
            SDB.createStatement();
            ArrayList<String> studentNames = SDB.queryGetStudentNames();
            SDB.close();
            studentListView.getItems().addAll(studentNames); //Adds the different studentNames
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // --- STUDENT BOTTOM MENU ---
        HBox studentBottomMenu = new HBox(20); // top menu in studentspanel
        studentBottomMenu.setPadding(new Insets(15, 12, 15, 12));
        studentBottomMenu.setSpacing(10); //spacing between buttons
        studentBottomMenu.setStyle("-fx-background-color: #336699;"); //color

        Button btnAddStudent = new Button("Add Student");
        btnAddStudent.setOnAction(e -> {
            ArrayList<String> studentInformation = AddStudentBox.display(); //returner en ArrayList<String> med information om en studerende
            if (!studentInformation.isEmpty()) {
                try { // VIRKER IKKE HELT. Retunerer 'query does not return ResultSet'
                    SDB.connect();
                    SDB.createStatement();
                    SDB.addStudent(studentInformation.get(0), studentInformation.get(1), studentInformation.get(2), studentInformation.get(3));
                    // UPDATE THE LIST HERE
                    SDB.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });

        Button btnRemoveStudent = new Button("Remove student");
        btnRemoveStudent.setOnAction(e -> {
            if (studentListView.getSelectionModel().getSelectedItem() != null){
                String message = "Are you sure you want to remove " + studentListView.getSelectionModel().getSelectedItem() + "?";
                boolean confirmation = ConfirmBox.display("Remove Student", message);
                if (confirmation){
                    System.out.println("REMOVE STUDENT " + studentListView.getSelectionModel().getSelectedItem()); // MAKE
                    try { // THIS IS WHERE THE STUDENT GETS REMOVED
                        SDB.connect();
                        SDB.createStatement();
                        SDB.removeStudent();
                        SDB.close();
                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        });
        studentBottomMenu.getChildren().addAll(btnAddStudent, btnRemoveStudent);


        // --- STUDENT BORDERPANE --- Layout for the student scene
        BorderPane studentLayout = new BorderPane();
        studentLayout.setTop(studentTopMenu);
        studentLayout.setLeft(studentListView);
        studentLayout.setCenter(information);
        studentLayout.setBottom(studentBottomMenu);


        // TOP MENU COURSE
        HBox courseTopMenu = new HBox(20); // top menu in studentspanel
        courseTopMenu.setPadding(new Insets(15, 12, 15, 12));
        courseTopMenu.setSpacing(10); //spacing between buttons
        courseTopMenu.setStyle("-fx-background-color: #336699;"); //changes the colour to blue for the top menu
        TextField searchCourse = new TextField();
        searchCourse.setPromptText("Search for a student");
        Button btnSearchButtonC = new Button("Search");
        btnSearchButtonC.setPrefSize(100, 20); //set the size of the button
        Button btnBackToMenuFromCourse = new Button("Back to menu");
        btnBackToMenuFromCourse.setPrefSize(100, 20); //set the size of the button
        courseTopMenu.getChildren().addAll(searchCourse, btnSearchButtonC, btnBackToMenuFromCourse);

        // -- LIST VIEW COURSE
        ListView<String> courseListView = new ListView<>(); // specify what type the list is holding. This one holds strings
        courseListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE); // can also be set to select multiple
        courseListView.setMinWidth(windowWidth / 3);
        try {
            SDB.connect();
            SDB.createStatement();
            ArrayList<String> courseNames = SDB.queryGetCourses();
            SDB.close();
            courseListView.getItems().addAll(courseNames); //Adds the different courses
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // -- TEXT-AREA COURSE
        TextArea informationCourse = new TextArea();
        informationCourse.setEditable(false);
        //informationCourse.setText(); // her skal information parses til
        informationCourse.setMaxWidth(windowWidth / 2);
        informationCourse.setPromptText("Information will be shown here");

        // ----- COURSE BOTTOM MENU
        HBox courseBottomMenu = new HBox(20);
        courseBottomMenu.setPadding(new Insets(15, 12, 15, 12));
        courseBottomMenu.setSpacing(10); //spacing between buttons
        courseBottomMenu.setStyle("-fx-background-color: #336699;"); //color

        Button btnAddCourse = new Button("Add Course");
        btnAddCourse.setOnAction(event -> AlertBox.display("Add Course", "im sorry, i haven't implemented this feature yet"));

        Button btnEditCourseInformation = new Button("Edit Course Information");
        btnEditCourseInformation.setOnAction(event -> {
            boolean test = EditCourse.display();
        });

        Button btnGetCourseInformation = new Button("Get Info");
        btnGetCourseInformation.setOnAction(event -> {
            try {
                SDB.connect();
                SDB.createStatement();
                SDB.selectedCourse = courseListView.getSelectionModel().getSelectedItem();
                informationCourse.setText(SDB.courseInfoQuery() + "\n" + SDB.AverageCourseGradeQuery());
                SDB.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        });
        courseBottomMenu.getChildren().addAll(btnAddCourse, btnGetCourseInformation, btnEditCourseInformation);


        // --- COURSE BORDERPANE --- Layout for the course scene
        BorderPane courseLayout = new BorderPane();
        courseLayout.setTop(courseTopMenu);
        courseLayout.setLeft(courseListView);
        courseLayout.setCenter(informationCourse);
        courseLayout.setBottom(courseBottomMenu);

        //##############################

        // - temporary main scene
        VBox mainLayout = new VBox(20); // used for mainScene
        Label mainWindowLabel = new Label("Main Window");
        mainLayout.setAlignment(Pos.CENTER);
        Button btnStudent = new Button("Administrate Students");
        Button btnCourse = new Button("Administrate Courses");
        Button exitButton = new Button("Exit");
        mainLayout.getChildren().addAll(mainWindowLabel, btnStudent, btnCourse, exitButton);


        studentScene = new Scene(studentLayout, windowWidth, windowHeight); // creates the main scene
        courseScene = new Scene(courseLayout, windowWidth, windowHeight);
        mainScene = new Scene(mainLayout, windowWidth, windowHeight);


        // ------------ FUNCTIONALITY ------------
        btnSearchButtonC.setOnAction(event -> {
            System.out.println(searchCourse.getText()); // prints what is searched for
            System.out.println(courseListView.getSelectionModel().getSelectedItem()); // prints what is selected from the list
        });
        btnSearchButton.setOnAction(event -> {
            System.out.println(searchCourse.getText()); // prints what is searched for
            System.out.println(studentListView.getSelectionModel().getSelectedItem()); // prints what is selected from the list
        });
        btnBackToMenuFromCourse.setOnAction(event -> { //uses a lambda expression to call the handle function inside the application class. The button press executes when is after ->. further more we can use lambda expressions to make multiple lines of code happen when pressing a button
            window.setScene(mainScene);
        });
        btnBackToMenu.setOnAction(event -> { //uses a lambda expression to call the handle function inside the application class. The button press executes when is after ->. further more we can use lambda expressions to make multiple lines of code happen when pressing a button
            window.setScene(mainScene);
        });

        exitButton.setOnAction(event -> closeProgram());
        window.setOnCloseRequest(event -> {
            //event.consume(); //allows us to control what happens when red X is pressed, instead of the closing happening no matter what
            //closeProgram(); // does such that, when the user hits the x in the upper corner, we still execute the closeProgram method
        });

        btnStudent.setOnAction(event -> window.setScene(studentScene));
        btnCourse.setOnAction(event -> window.setScene(courseScene));

        window.setScene(mainScene);
        window.show();

    } // ### JavaFX Main code stops here ###

    public void closeProgram() { //method for closing the program
        System.out.println("Closing program");
        boolean confirmation = ConfirmBox.display("ATTENTION", "Are you sure you want to close?");
        if (confirmation) {
            System.out.println("DO SOMETHING HERE BEFORE THE PROGRAM CLOSES");
            window.close();
            System.out.println("Program closed");
        } else {
            System.out.println("Program is not closing");
        }
    }
}
