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


public class LoginController implements Initializable {

    @FXML
    private AnchorPane login_pane;
    @FXML
    private Button loginBtn;
    @FXML
    private Button signUpBtn;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    String adminEmail = "a";
    String adminPassword = "a";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatabaseHandler handler = DatabaseHandler.getInstance();
    }

    @FXML
    public void login(){
        String  userEmail = email.getText();
        String userPassword = password.getText();

        if(userEmail.isEmpty() || userPassword.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Invalid Email or Password");
            alert.showAndWait();

        }
        else{
            if(userEmail.equals(adminEmail) && userPassword.equals(adminPassword)){
                loadWindow("/library/main/AdminDashboard.fxml", "AdminDashBoard",true);
            }
            else{
                loadWindow("/library/main/Dashboard.fxml", "DashBoard", true);
            }
            closeWindow(login_pane);
        }

    }

    @FXML
    void goToSignUp(ActionEvent event){
        loadWindow("/library/main/SignUp.fxml", "SignUp",false);
        closeWindow(login_pane);
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
