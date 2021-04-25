import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {
    public static void display (String title, String message){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //does so that the AlertBox must be dealt with when it pops up
        window.setTitle(title);
        window.setMinWidth(250); // er ikke sikker på hvad gør
        Label label = new Label(message);
        Button button = new Button("Close");
        button.setOnAction(e -> window.close()); //closes the window when the button is pressed

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, button);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait(); //is seen alot in conjunction with line 12, since it implies that we need to handle the window before going back
    }
}
