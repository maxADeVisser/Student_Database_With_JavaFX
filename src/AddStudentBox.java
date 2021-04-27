import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.ArrayList;

public class AddStudentBox {
    private static ArrayList<String> studentNames;

    public static ArrayList<String> display (){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //does so that the AlertBox must be dealt with when it pops up
        window.setTitle("Add Student");
        window.setMinWidth(400);

        studentNames = new ArrayList<>();

        Label label = new Label("Enter the information below to add a student");

        //REMEBER TO VERIFY INPUTS FROM USER!!!!!!!!!!!!!!!!!!!!!!
        TextField idTextfield = new TextField();
        idTextfield.setMaxWidth(200);
        idTextfield.setPromptText("ID");

        TextField surnameTextfield = new TextField();
        surnameTextfield.setMaxWidth(200);
        surnameTextfield.setPromptText("Surname");

        TextField lastnameTextfield = new TextField();
        lastnameTextfield.setMaxWidth(200);
        lastnameTextfield.setPromptText("Lastname");

        TextField cityTextfield = new TextField();
        cityTextfield.setMaxWidth(200);
        cityTextfield.setPromptText("City");

        Button addButton = new Button("Add");
        addButton.setOnAction(e ->{
            studentNames.add(idTextfield.getText());
            studentNames.add(surnameTextfield.getText());
            studentNames.add(lastnameTextfield.getText());
            studentNames.add(cityTextfield.getText());
            window.close();
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> {
            studentNames.clear(); // Clears the arrayList
            window.close();
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15, 12, 15, 12));
        layout.getChildren().addAll(label, idTextfield, surnameTextfield, lastnameTextfield, cityTextfield, addButton, cancelButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return studentNames;
    }
}
