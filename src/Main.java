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

    Stage window; // Main Window
    Scene studentScene, courseScene, mainScene;

    int windowWidth = 800; //Width of the application
    int windowHeight = 500; // Height of the application

    public static void main(String[] args) {
        launch(args); //a method inside the Application class that launches javaFX application
    }

    @Override
    public void start(Stage primaryStage) throws Exception { //all the code inside here is the main javaFX code

        String url = "jdbc:sqlite:/Users/sebastian/IdeaProjects/forsøg1eksamen/Students.db";
        StudentModel SDB = new StudentModel(url);

        window = primaryStage; //renaming for easier understanding of code
        window.setTitle("Student Database System"); //sets the title of the window

        // ####################################
        //  STUDENT SCENE :
        // ####################################
        // -- TEXT-AREA STUDENTSCENE
        TextArea information = new TextArea();
        information.setEditable(false); // does so that TextArea is not edible
        information.setMaxWidth(windowWidth / 2);
        information.setPromptText("Information will be shown here");

        // -- STUDENTSCENE TOP MENU
        HBox studentTopMenu = new HBox(20); // top menu in studentspanel
        studentTopMenu.setPadding(new Insets(15, 12, 15, 12));
        studentTopMenu.setSpacing(10); //spacing between buttons
        studentTopMenu.setStyle("-fx-background-color: #336699;"); //color

        Button btnBackToMenu = new Button("Back");
        btnBackToMenu.setPrefSize(100, 20); //set the size of the button
        btnBackToMenu.setOnAction(event -> { //uses a lambda expression to call the handle function inside the application class. The button press executes when is after ->. further more we can use lambda expressions to make multiple lines of code happen when pressing a button
            window.setScene(mainScene);
        });
        studentTopMenu.getChildren().add(btnBackToMenu);

        // -- STUDENTSCENE LIST VIEW
        ListView<String> studentListView = new ListView<>(); // specify what type the list is holding. This one holds strings
        studentListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE); // can also be set to select multiple
        studentListView.setMinWidth(windowWidth / 3);
        connectToDataBase(SDB);
        ArrayList<String> studentNames = SDB.queryGetStudentNames();
        studentListView.getItems().addAll(studentNames); //Adds the different studentNames
        closeConnectionToDataBase(SDB);

        // --- STUDENT BOTTOM MENU ---
        HBox studentBottomMenu = new HBox(20); // top menu in studentspanel
        studentBottomMenu.setPadding(new Insets(15, 12, 15, 12));
        studentBottomMenu.setSpacing(10); //spacing between buttons
        studentBottomMenu.setStyle("-fx-background-color: #336699;"); //color

        // -- STUDENTSCENE GET STUDENTINFORMATION BUTTON
        studentListView.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if (studentListView.getSelectionModel().getSelectedItem() != null) {
                connectToDataBase(SDB);
                SDB.selectedStudent = studentListView.getSelectionModel().getSelectedItem();
                information.setText(SDB.studentCoursesGradeQuery() + "\n" + SDB.averageStudentGradeQuery());
                closeConnectionToDataBase(SDB);
            }
        });
        //studentBottomMenu.getChildren().addAll(btnGetStudentInformation);


        // --- STUDENTSCENE LAYOUT BORDERPANE ---
        BorderPane studentLayout = new BorderPane();
        studentLayout.setTop(studentTopMenu);
        studentLayout.setLeft(studentListView);
        studentLayout.setCenter(information);
        studentLayout.setBottom(studentBottomMenu);


        // ####################################
        //  COURSE SCENE :
        // ####################################

        // TOP MENU COURSE
        HBox courseTopMenu = new HBox(20); // top menu in studentspanel
        courseTopMenu.setPadding(new Insets(15, 12, 15, 12));
        courseTopMenu.setSpacing(10); //spacing between buttons
        courseTopMenu.setStyle("-fx-background-color: #336699;"); //changes the colour to blue for the top menu
        Button btnBackToMenuFromCourse = new Button("Back");
        btnBackToMenuFromCourse.setPrefSize(100, 20); //set the size of the button
        btnBackToMenuFromCourse.setOnAction(event -> { //uses a lambda expression to call the handle function inside the application class. The button press executes when is after ->. further more we can use lambda expressions to make multiple lines of code happen when pressing a button
            window.setScene(mainScene);
        });
        courseTopMenu.getChildren().addAll(btnBackToMenuFromCourse);

        // -- COURSE LIST VIEW
        ListView<String> courseListView = new ListView<>(); // specify what type the list is holding. This one holds strings
        courseListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE); // can also be set to select multiple
        courseListView.setMinWidth(windowWidth / 3);
        connectToDataBase(SDB);
        ArrayList<String> courseNames = SDB.queryGetCourses();
        courseListView.getItems().addAll(courseNames); //Adds the different courses
        closeConnectionToDataBase(SDB);

        // -- TEXT-AREA COURSE
        TextArea informationCourse = new TextArea();
        informationCourse.setEditable(false);
        informationCourse.setMaxWidth(windowWidth / 2);
        informationCourse.setPromptText("Information will be shown here");

        // ----- COURSE BOTTOM MENU
        HBox courseBottomMenu = new HBox(20);
        courseBottomMenu.setPadding(new Insets(15, 12, 15, 12));
        courseBottomMenu.setSpacing(10); //spacing between buttons
        courseBottomMenu.setStyle("-fx-background-color: #336699;"); //color

        Button btnEditCourseInformation = new Button("Edit Course Information");
        btnEditCourseInformation.setOnAction(event -> {
            if (courseListView.getSelectionModel().getSelectedItem() != null) {
                SDB.selectedCourse = courseListView.getSelectionModel().getSelectedItem();
                EditCourse.display(SDB);
            }
        });

        courseListView.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            connectToDataBase(SDB);
            SDB.selectedCourse = courseListView.getSelectionModel().getSelectedItem();
            informationCourse.setText(SDB.courseInfoQuery() + "\n" + SDB.averageCourseGradeQuery());
            closeConnectionToDataBase(SDB);
        });
        courseBottomMenu.getChildren().add(btnEditCourseInformation);

        // --- COURSE BORDERPANE --- Layout for the course scene
        BorderPane courseLayout = new BorderPane();
        courseLayout.setTop(courseTopMenu);
        courseLayout.setLeft(courseListView);
        courseLayout.setCenter(informationCourse);
        courseLayout.setBottom(courseBottomMenu);

        // - temporary main scene
        VBox mainLayout = new VBox(20); // used for mainScene
        mainLayout.setAlignment(Pos.CENTER);
        Button btnStudent = new Button("Students Menu");
        Button btnCourse = new Button("Courses Menu");
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(event -> closeProgram());
        mainLayout.getChildren().addAll(btnStudent, btnCourse, exitButton);

        // --- D
        studentScene = new Scene(studentLayout, windowWidth, windowHeight); // creates the main scene
        courseScene = new Scene(courseLayout, windowWidth, windowHeight);
        mainScene = new Scene(mainLayout, windowWidth, windowHeight);


        window.setOnCloseRequest(event -> { // does so that we can consume the closing event when pressing the red x in the corner, and still below codeblock
            //event.consume(); //allows us to control what happens when red X is pressed, instead of the closing happening no matter what
            //closeProgram(); // does such that, when the user hits the x in the upper corner, we still execute the closeProgram method
        });

        btnStudent.setOnAction(event -> window.setScene(studentScene)); // changes scene to studentScene
        btnCourse.setOnAction(event -> window.setScene(courseScene)); // changes scene to courseScene

        window.setScene(mainScene);
        window.show();

    } // ### JavaFX Main code stops here ###


    public void connectToDataBase(StudentModel SDB){
        try{
            SDB.connect();
            SDB.createStatement();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void closeConnectionToDataBase(StudentModel SDB){
        try{
            SDB.close();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void closeProgram() { //method for closing the program
        boolean confirmation = ConfirmBox.display("ATTENTION", "Are you sure you want to close?");
        if (confirmation) {
            // IF NEEDED, DO SOMETHING HERE BEFORE THE PROGRAM CLOSES
            window.close();
        }
    }
}
