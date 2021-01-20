package library.books;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class bookLoader extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("AddBook.fxml"));
        primaryStage.setTitle("Library Assistant");
        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


}
