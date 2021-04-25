import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application { //Application is the class holding all the javaFX stuff. So we always need to inherit it to use javaFX. futhermore everytime we want to handle a user input, we implement the EventHandler
    Stage window; //main window
    // --- Below Variables needs to be global because multiple functions are handling them:
    Scene studentScene, mainScene; //different scenes
    Button exitButton, mainButton; //buttons need to be global since we need to handle them in different functions

    public static void main(String[] args) {
        launch(args); //a method inside the Application class that launches javaFX application
    }

    @Override
    public void start(Stage primaryStage) throws Exception { //all the code inside here is the main javaFX code
        window = primaryStage; //renaming for easier understanding of code
        window.setTitle("Student Database System"); //sets the title of the window

        // ----- Initializing Objects -------
        exitButton = new Button("Exit"); // a button for closing the program. Its better too use this, since there probably is some code we want to run just before closing the program
        mainButton = new Button("Student");

        /* 
        // main
        GridPane studentLayout = new GridPane(); // used for main scene
        studentLayout.setPadding(new Insets(10,10,10,10)); // all buttons are 10 pixels from the edge of the application
        studentLayout.setVgap(10);
        studentLayout.setHgap(5);

        Label studentWinLabel = new Label("Student Window");
        TextField textfield = new TextField(); // for searching for students
        textfield.setPromptText("Search student"); //greyed out text in textfield
        GridPane.setConstraints(studentWinLabel, 1,0);
        GridPane.setConstraints(textfield,1,2);
        GridPane.setConstraints(button,2,2);
        GridPane.setConstraints(exitButton,3,3);
        studentLayout.getChildren().addAll(studentWinLabel, textfield, button, exitButton);
         */

        //STUDENT TOP MENU
        HBox topMenu = new HBox(20); // top menu in studentspanel
        topMenu.setPadding(new Insets(15, 12, 15, 12));
        topMenu.setSpacing(10); //spacing between buttons
        topMenu.setStyle("-fx-background-color: #336699;"); //changes the colour to blue for the top menu
        Label lbSearchStudent = new Label("Insert a student:"); //ved ikke om skal slettes
        Button btnSearchButton = new Button("Search");
        btnSearchButton.setPrefSize(100, 20); //set the size of the button
        Button btnBackToMenu = new Button("Back to menu");
        btnBackToMenu.setPrefSize(100, 20); //set the size of the button
        TextField searchStudent = new TextField();
        searchStudent.setPromptText("Search for a student");
        topMenu.getChildren().addAll(lbSearchStudent, searchStudent, btnSearchButton, btnBackToMenu);

        //STUDENT LEFT MENU


        // --- STUDENT BORDERPANE ---
        BorderPane studentLayout = new BorderPane();
        studentLayout.setTop(topMenu);



        VBox mainLayout = new VBox(20); // used for mainScene
        Label mainWindowLabel = new Label("Main Window");
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.getChildren().addAll(mainWindowLabel, mainButton);


        studentScene = new Scene(studentLayout, 800,500); // creates the main scene
        mainScene = new Scene(mainLayout,500,300);


        // ------------ FUNCTIONALITY ------------
        btnBackToMenu.setOnAction(event -> { //uses a lambda expression to call the handle function inside the application class. The button press executes when is after ->. further more we can use lambda expressions to make multiple lines of code happen when pressing a button
            //AlertBox.display("ALERT!", "You are trying to enter a new scene, but you are too cool");
            window.setScene(mainScene);
            System.out.println("Changed to Main Menu");
        });
        exitButton.setOnAction(event -> closeProgram());
        window.setOnCloseRequest(event -> {
            //event.consume(); //allows us to control what happens when red X is pressed, instead of the closing happening no matter what
            //closeProgram(); // does such that, when the user hits the x in the upper corner, we still execute the closeProgram method
        });
        mainButton.setOnAction(event -> window.setScene(studentScene));

        window.setScene(studentScene); // initially sets the scene to the primaryScene
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

/* ------ NOTES -------
    - A entire window is called a Stage
    - The content inside the window is called the Scene
    - The Stackpane is just one type of layout
    - A Label is static text that the user cant interact with

    -


 */
