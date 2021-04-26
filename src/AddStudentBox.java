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
    private static String name;

    public static String display (String title, String message){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //does so that the AlertBox must be dealt with when it pops up
        window.setTitle(title);
        window.setMinWidth(400); //size of the window
        Label label = new Label(message);

        TextField nameTextfield = new TextField();
        nameTextfield.setMaxWidth(200);
        nameTextfield.setPromptText("Enter a name");

        TextField cityTextfield = new TextField();
        cityTextfield.setMaxWidth(200);
        cityTextfield.setPromptText("City");

        Button addButton = new Button("Add");
        addButton.setOnAction(e ->{
            name = nameTextfield.getText();
            window.close();
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> {
            window.close();
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15, 12, 15, 12));
        layout.getChildren().addAll(label, nameTextfield, cityTextfield, addButton, cancelButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait(); //is seen alot in conjunction with line 12, since it implies that we need to handle the window before going back

        return name;
    }
}
