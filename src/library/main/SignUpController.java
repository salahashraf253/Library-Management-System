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

public class SignUpController implements Initializable {

    @FXML
    private AnchorPane signUp_pane;
    @FXML
    private TextField full_name;
    @FXML
    private Label name_lbl;
    @FXML
    private TextField address;
    @FXML
    private Label address_lbl;
    @FXML
    private TextField mobile;
    @FXML
    private Label mobile_lbl;
    @FXML
    private TextField email;
    @FXML
    private Label email_lbl;
    @FXML
    private PasswordField password;
    @FXML
    private Label password_lbl;
    @FXML
    private Button signUpBtn;
    @FXML
    private Button loginBtn;

    DatabaseHandler handler;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       handler = DatabaseHandler.getInstance();
    }

    public void enterPressedTextField(TextField txt, TextField nxt, Label lbl){
        txt.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)|| ke.getCode().equals(KeyCode.DOWN))
            {
                if (txt.getText().isEmpty()){
                    lbl.setVisible(true);
                }
                else{
                    lbl.setVisible(false);
                    nxt.requestFocus();
                }
            }
        });
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
    public void enterPressedName(){
        enterPressedTextField(full_name,address,name_lbl);
    }
    @FXML
    public void enterPressedAddress(){
        enterPressedTextField(address,mobile,address_lbl);
    }
    @FXML
    public void enterPressedMobile(){
        enterPressedTextField(mobile,email,mobile_lbl);
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
                   signUp();
                }
        });
    }

    @FXML
    public void signUp(){
        String userFullName = full_name.getText();
        String userAddress = address.getText();
        String userMobile = mobile.getText();
        String  userEmail = email.getText();
        String userPassword = password.getText();

       if(userFullName.isEmpty() || userAddress.isEmpty() || userMobile.isEmpty()||userEmail.isEmpty() || userPassword.isEmpty()) {
           if (userFullName.isEmpty())
               name_lbl.setVisible(true);
           if (userAddress.isEmpty())
               address.setVisible(true);
           if (userMobile.isEmpty())
               mobile.setVisible(true);
           if (userEmail.isEmpty())
               email_lbl.setVisible(true);
           if (userPassword.isEmpty())
               password_lbl.setVisible(true);
       }
        else{
           name_lbl.setVisible(false);
           address_lbl.setVisible(false);
           mobile_lbl.setVisible(false);
           email_lbl.setVisible(false);
           password_lbl.setVisible(false);

           String qu = "INSERT INTO MEMBER VALUES(" +
                   "'" + userFullName + "'," +
                   "'" + userAddress + "'," +
                   "'" + userMobile + "'," +
                   "'" + userEmail + "'," +
                   "'" + userPassword + "'," +
                   "" + true + "" + ")";
           System.out.println(qu);
           handler.execAction(qu);
           if (handler.execAction(qu)) {
               Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
               alert.setHeaderText(null);
               alert.setContentText("id");
               alert.showAndWait();

               loadWindow("/library/main/Dashboard.fxml", "DashBoard",true);
               closeWindow(signUp_pane);
           }
           else{
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setHeaderText(null);
               alert.setContentText("Failed");
               alert.showAndWait();
           }
        }
    }


    @FXML
    void goToLogin(){
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
