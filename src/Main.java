import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application { //Application is the class holding all the javaFX stuff. So we always need to inherit it to use javaFX. futhermore everytime we want to handle a user input, we implement the EventHandler

    // --- Below Variables needs to be global because multiple functions are handling them:
    Stage window; //main window
    Scene primaryScene, secondaryScene; //different scenes
    Button button, button2, exitButton; //buttons need to be global since we need to handle them in different functions

    public static void main(String[] args) {
        launch(args); //a method inside the Application class that launches javaFX application
    }

    @Override
    public void start(Stage primaryStage) throws Exception { //all the code inside here is the main javaFX code
        window = primaryStage; //skal fjernes igen
        window.setTitle("Student Database System"); //sets the title of the window

        // ----- Initializing Objects -------
        button = new Button("Go to scene 2"); //creates a button
        button2 = new Button("Go back to scene 1 again");
        exitButton = new Button("Exit"); // a button for closing the program. Its better too use this, since there probably is some code we want to run just before closing the program

        Label label1 = new Label("Welcome to the Main Window"); // creates a label (A static text)
        Label label2 = new Label("secondary scene");

        VBox layout = new VBox(20); // used for mainScene
        layout.getChildren().addAll(label1, button, exitButton);


        VBox layout1 = new VBox(20); // used for secondary
        layout1.getChildren().addAll(label2, button2);

        primaryScene = new Scene(layout, 500,300); // creates the main scene
        secondaryScene = new Scene(layout1, 500,300); // creates the second scene


        // ------------ FUNCTIONALITY ------------
        button.setOnAction(event -> { //uses a lambda expression to call the handle function inside the application class. The button press executes when is after ->. further more we can use lambda expressions to make multiple lines of code happen when pressing a button
            //AlertBox.display("ALERT!", "You are trying to enter a new scene, but you are too cool");
            window.setScene(secondaryScene);
            System.out.println("Changed to secondaryScene");
        });
        button2.setOnAction(event -> {
            window.setScene(primaryScene);
            System.out.println("Changed to primaryScene");
        });

        exitButton.setOnAction(event -> closeProgram());
        window.setOnCloseRequest(event -> {
            event.consume(); //allows us to control what happens when red X is pressed, instead of the closing happening no matter what
            closeProgram(); // does such that, when the user hits the x in the upper corner, we still execute the closeProgram method
        });

        window.setScene(primaryScene); // initially sets the scene to the primaryScene
        window.show(); // opens the application window

    } // ### JavaFX code stops here ###

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