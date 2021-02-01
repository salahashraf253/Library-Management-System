package library.main;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    int screenWidth = (int) Screen.getPrimary().getBounds().getWidth();
    int screenHeight = (int) Screen.getPrimary().getBounds().getHeight();

    Stage stage;
    Scene scene;

    int initialX;
    int initialY;

    @Override
    public void start(Stage stage) throws Exception {

        // Scene
        Parent root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
        stage.setTitle("Library Assistant");
        stage.initStyle(StageStyle.DECORATED);
        scene = new Scene(root);

        // Moving
        scene.setOnMousePressed(m -> {
            if (m.getButton() == MouseButton.PRIMARY) {
                scene.setCursor(Cursor.MOVE);
                initialX = (int) (stage.getX() - m.getScreenX());
                initialY = (int) (stage.getY() - m.getScreenY());
            }
        });

        scene.setOnMouseDragged(m -> {
            if (m.getButton() == MouseButton.PRIMARY) {
                stage.setX(m.getScreenX() + initialX);
                stage.setY(m.getScreenY() + initialY);
            }
        });

        scene.setOnMouseReleased(m -> {
            scene.setCursor(Cursor.DEFAULT);
        });

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}


