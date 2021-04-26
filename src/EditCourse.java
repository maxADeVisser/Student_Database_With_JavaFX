import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditCourse {
    private static boolean result;

    public static boolean display (int windowWidth, int windowHeight){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //does so that the AlertBox must be dealt with when it pops up
        window.setTitle("Edit Course");
        window.setMinWidth(windowWidth);
        window.setMinHeight(windowHeight);

        Label label = new Label("Edit the course");
        ListView<String> studentList = new ListView<>(); //JJEG KOM HEREREEER TITIIIIL

        GridPane a = new GridPane();
        a.setMinSize(windowWidth,windowHeight);
        a.setPadding(new Insets(10, 10, 10, 10));
        a.setVgap(5);
        a.setHgap(5);
        a.setAlignment(Pos.CENTER);
        a.add(label,0,0);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15, 12, 15, 12));
        layout.setSpacing(10);
        layout.getChildren().addAll(label);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait(); //is seen alot in conjunction with line 12, since it implies that we need to handle the window before going back

        return result;
    }
}


