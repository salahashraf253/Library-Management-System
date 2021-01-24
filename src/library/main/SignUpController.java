package library.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import library.database.DatabaseHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private AnchorPane signUp_pane;
    @FXML
    private TextField full_name;
    @FXML
    private TextField address;
    @FXML
    private TextField mobile;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private Button signUpBtn;
    @FXML
    private Button loginBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatabaseHandler handler = DatabaseHandler.getInstance();
    }

    @FXML
    public void signUp(){
        String userFullName = full_name.getText();
        String userAddress = address.getText();
        String userMobile = mobile.getText();
        String  userEmail = email.getText();
        String userPassword = password.getText();

       if(userFullName.isEmpty() || userAddress.isEmpty() || userMobile.isEmpty()||userEmail.isEmpty() || userPassword.isEmpty()) {
           Alert alert = new Alert(Alert.AlertType.WARNING);
           alert.setHeaderText(null);
           alert.setContentText("All fields cannot be empty!");
           alert.showAndWait();
        }
        else{
           loadWindow("/library/main/Dashboard.fxml", "DashBoard",true);
           closeWindow(signUp_pane);
        }

    }

    @FXML
    void goToLogin(ActionEvent event){
        loadWindow("/library/main/Login.fxml", "Login",false);
        closeWindow(signUp_pane);
    }
    void loadWindow(String loc, String title,Boolean max){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.setMaximized(max);
            stage.show();
        } catch (IOException e) {
            //Logger.getLogger(AddAdminController.class.getName().log(Level.SEVERE, null, ex));
            e.printStackTrace();
        }
    }
    void closeWindow(AnchorPane pane){
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }



}
