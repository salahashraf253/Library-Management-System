package library.main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
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
    private TextField email;
    @FXML
    private Label email_lbl;
    @FXML
    private PasswordField password;
    @FXML
    private Label password_lbl;
    @FXML
    private Button loginBtn;
    @FXML
    private Button signUpBtn;

    String adminEmail = "a";
    String adminPassword = "a";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatabaseHandler handler = DatabaseHandler.getInstance();
    }

    public void enterPressedTextField(TextField txt, PasswordField pass, Label lbl){
        txt.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)|| ke.getCode().equals(KeyCode.DOWN))
            {
                if (txt.getText().isEmpty()){
                    lbl.setVisible(true);
                }
                else{
                    lbl.setVisible(false);
                    pass.requestFocus();
                }
            }
        });
    }

    @FXML
    public void enterPressedEmail(){
        enterPressedTextField(email,password,email_lbl);
    }
    @FXML
    public void enterPressedPassword(){
        password.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER))
            {
                login();
            }
        });
    }

 /*   private void loginVarification(){
        DatabaseHandler handler = DatabaseHandler.getInstance();

        String qu="SELECT FROM MEMBER WHERE email = userEmail and pass = userPassword";
        handler.execAction(qu);
        if(handler.execAction(qu)){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("id");
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Failed");
            alert.showAndWait();

        }


    }*/

    @FXML
    public void login(){
        String  userEmail = email.getText();
        String userPassword = password.getText();
        if (userEmail.isEmpty() || userPassword.isEmpty()){
            if (userEmail.isEmpty())
                email_lbl.setVisible(true);
            if (userPassword.isEmpty())
                password_lbl.setVisible(true);
        }
        else{
            email_lbl.setVisible(false);
            password_lbl.setVisible(false);

            if(userEmail.equals(adminEmail) && userPassword.equals(adminPassword))
                loadWindow("/library/main/AdminDashboard.fxml", "AdminDashBoard",true);
            else
                loadWindow("/library/main/Dashboard.fxml", "DashBoard", true);

            closeWindow(login_pane);
        }
    }

    @FXML
    void goToSignUp(){
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
