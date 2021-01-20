package library.main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SettingsController {

    @FXML
    private AnchorPane settingsPane;
    @FXML
    private JFXButton exitBtn;
    @FXML
    private JFXTextField adminEmail;
    @FXML
    private JFXPasswordField adminPassword;
    @FXML
    private JFXButton saveBtn;
    @FXML
    private JFXButton cancelBtn;

    @FXML
    private void cancel (ActionEvent event){
        Stage stage = (Stage) settingsPane.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void save(){

    }
}
