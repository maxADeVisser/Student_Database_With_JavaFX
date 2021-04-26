import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application { //Application is the class holding all the javaFX stuff. So we always need to inherit it to use javaFX. futhermore everytime we want to handle a user input, we implement the EventHandler
    //Global Variables:
    Stage window; //main window
    Scene studentScene, courseScene, mainScene;

    int windowWidth = 800;
    int windowHeight = 500;

    public static void main(String[] args) {
        launch(args); //a method inside the Application class that launches javaFX application
    }

    @Override
    public void start(Stage primaryStage) throws Exception { //all the code inside here is the main javaFX code
        window = primaryStage; //renaming for easier understanding of code
        window.setTitle("Student Database System"); //sets the title of the window

        // -- LIST VIEW STUDENTS
        ListView<String> studentListView = new ListView<>(); // specify what type the list is holding. This one holds strings
        studentListView.getItems().addAll("Aisha", "Albert", "Sine", "Per"); // skal addes fra databasen
        studentListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE); // can also be set to select multiple
        studentListView.setMinWidth(windowWidth/3);

        // -- TEXTAREA STUDENTS
        TextArea information = new TextArea();
        information.setEditable(false);
        //information.setText("test"); // her skal information parses til
        information.setMaxWidth(windowWidth/2);
        information.setPromptText("Information will be shown here");

        //STUDENT TOP MENU
        HBox studentTopMenu = new HBox(20); // top menu in studentspanel
        studentTopMenu.setPadding(new Insets(15, 12, 15, 12));
        studentTopMenu.setSpacing(10); //spacing between buttons
        studentTopMenu.setStyle("-fx-background-color: #336699;"); //changes the colour to blue for the top menu
        Label lbSearchStudent = new Label("Insert a student:"); //ved ikke om skal slettes
        TextField searchStudent = new TextField();
        searchStudent.setPromptText("Search for a student");
        Button btnSearchButton = new Button("Search");
        btnSearchButton.setPrefSize(100, 20); //set the size of the button
        Button btnBackToMenu = new Button("Back to menu");
        btnBackToMenu.setPrefSize(100, 20); //set the size of the button
        studentTopMenu.getChildren().addAll(lbSearchStudent, searchStudent, btnSearchButton, btnBackToMenu);

        // --- STUDENT BORDERPANE --- Layout for the student scene
        BorderPane studentLayout = new BorderPane();
        studentLayout.setTop(studentTopMenu);
        studentLayout.setLeft(studentListView);
        studentLayout.setCenter(information);








        // TOP MENU COURSE
        HBox courseTopMenu = new HBox(20); // top menu in studentspanel
        courseTopMenu.setPadding(new Insets(15, 12, 15, 12));
        courseTopMenu.setSpacing(10); //spacing between buttons
        courseTopMenu.setStyle("-fx-background-color: #336699;"); //changes the colour to blue for the top menu
        Label lbSearchCourse = new Label("Insert a student:"); //ved ikke om skal slettes
        TextField searchCourse = new TextField();
        searchCourse.setPromptText("Search for a student");
        Button btnSearchButtonC = new Button("Search");
        btnSearchButtonC.setPrefSize(100, 20); //set the size of the button
        Button btnBackToMenuFromCourse = new Button("Back to menu");
        btnBackToMenuFromCourse.setPrefSize(100, 20); //set the size of the button
        courseTopMenu.getChildren().addAll(lbSearchCourse, searchCourse, btnSearchButtonC, btnBackToMenuFromCourse);

        // -- LIST VIEW COURSE
        ListView<String> courseListView = new ListView<>(); // specify what type the list is holding. This one holds strings
        courseListView.getItems().addAll("Software Development", "Essential Computing 1"); // skal addes fra databasen
        courseListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE); // can also be set to select multiple
        courseListView.setMinWidth(windowWidth/3);

        // -- TEXTAREA COURSE
        TextArea informationCourse = new TextArea();
        informationCourse.setEditable(false);
        informationCourse.setText("Her er der information om kurserne"); // her skal information parses til
        informationCourse.setMaxWidth(windowWidth/2);
        informationCourse.setPromptText("Information will be shown here");

        // --- COURSE BORDERPANE --- Layout for the course scene
        BorderPane courseLayout = new BorderPane();
        courseLayout.setTop(courseTopMenu);
        courseLayout.setLeft(courseListView);
        courseLayout.setCenter(informationCourse);







        // - temporary main scene
        VBox mainLayout = new VBox(20); // used for mainScene
        Label mainWindowLabel = new Label("Main Window");
        mainLayout.setAlignment(Pos.CENTER);
        Button btnStudent = new Button("Administrate Students");
        Button btnCourse = new Button("Administrate Courses");
        Button exitButton = new Button("Exit");
        mainLayout.getChildren().addAll(mainWindowLabel, btnStudent,btnCourse, exitButton);


        // DEN HER SKAL SLETTES OG ER KUN TEMPORARY. Blvier brugt til Courses scene
        HBox slet = new HBox(20); // top menu in studentspanel
        slet.setPadding(new Insets(15, 12, 15, 12));
        slet.setSpacing(10);
        slet.setStyle("-fx-background-color: #336699;"); //changes the colour to blue for the top menu


        studentScene = new Scene(studentLayout, windowWidth,windowHeight); // creates the main scene
        courseScene = new Scene (courseLayout, windowWidth, windowHeight);
        mainScene = new Scene(mainLayout,windowWidth,windowHeight);


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
            System.out.println("Changed to Main Menu");
        });
        btnBackToMenu.setOnAction(event -> { //uses a lambda expression to call the handle function inside the application class. The button press executes when is after ->. further more we can use lambda expressions to make multiple lines of code happen when pressing a button
            window.setScene(mainScene);
            System.out.println("Changed to Main Menu");
        });

        exitButton.setOnAction(event -> closeProgram());
        window.setOnCloseRequest(event -> {
            event.consume(); //allows us to control what happens when red X is pressed, instead of the closing happening no matter what
            closeProgram(); // does such that, when the user hits the x in the upper corner, we still execute the closeProgram method
        });

        btnStudent.setOnAction(event -> window.setScene(studentScene));
        btnCourse.setOnAction(event -> window.setScene(courseScene));

        window.setScene(mainScene); // initially sets the scene to the primaryScene
        window.show(); // opens the application window

    } // ### JavaFX Main code stops here ###

    public void closeProgram(){ //method for closing the program
        System.out.println("Closing program");
        boolean confirmation = ConfirmBox.display("ATTENTION", "Are you sure you want to close?");
        if (confirmation){
            System.out.println("DO SOMETHING HERE BEFORE THE PROGRAM CLOSES");
            window.close();
            System.out.println("Program closed");
        } else {
            System.out.println("Program is not closing");
        }
    }
}
