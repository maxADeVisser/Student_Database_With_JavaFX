import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {
    private static boolean result;

    public static boolean display (String title, String message){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //does so that the AlertBox must be dealt with when it pops up
        window.setTitle(title);
        window.setMinWidth(250);
        Label label = new Label(message);

        Button yesButton = new Button("Yes");
        yesButton.setOnAction(e -> {
            result = true;
            window.close();
        });
        Button noButton = new Button("No");
        noButton.setOnAction(e -> {
            result = false;
            window.close();
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15, 12, 15, 12));
        layout.getChildren().addAll(label, yesButton, noButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait(); //is seen alot in conjunction with line 12, since it implies that we need to handle the window before going back

        return result;
    }
}
