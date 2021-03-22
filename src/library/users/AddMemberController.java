package library.users;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import library.database.DatabaseConnection;

import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;


public class AddMemberController implements Initializable {
    @FXML
    private AnchorPane addMemberPane;
    @FXML
    private JFXTextField first_name;
    @FXML
    private JFXTextField last_name;
    @FXML
    private Label first_name_lbl;
    @FXML
    private Label last_name_lbl;
    @FXML
    private JFXTextField address;
    @FXML
    private Label address_lbl;
    @FXML
    private JFXTextField mobile;
    @FXML
    private Label mobile_lbl;
    @FXML
    private JFXTextField email;
    @FXML
    private Label email_lbl;
    @FXML
    private  JFXTextField password;
    @FXML
    private Label password_lbl;
    @FXML
    private  JFXTextField confirm_password;
    @FXML
    private Label confirm_password_lbl;
    @FXML
    private JFXButton addMemberBtn;
    @FXML
    private JFXButton cancelBtn;

    final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    final String regex = "^(?:01)[0-9]{9}$";

    DatabaseConnection connectNow = new DatabaseConnection();
    Connection connectDB = connectNow.getConnection();
    Statement stmt;
    public void initialize(URL url, ResourceBundle rb) {

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
    public void enterPressedFirstName(){
        enterPressedTextField(first_name,last_name,first_name_lbl);
    }
    @FXML
    public void enterPressedLastName(){
        enterPressedTextField(last_name,address,last_name_lbl);
    }
    @FXML
    public void enterPressedAddress(){
        enterPressedTextField(address,mobile,address_lbl);
    }
    @FXML
    public void enterPressedMobile() {
        if (!mobile.getText().matches(regex)){
            mobile_lbl.setText("*Invalid  Mobile Phone number");
            mobile_lbl.setVisible(true);
            mobile.requestFocus();
        }
        else{
            mobile_lbl.setVisible(false);
            enterPressedTextField(mobile, email, mobile_lbl);
        }
    }
    @FXML
    public void enterPressedEmail(){
        if (!email.getText().matches(emailPattern)) {
            email_lbl.setText("*Invalid email address");
            email_lbl.setVisible(true);
            email.requestFocus();
        }
        else {
            email_lbl.setVisible(false);
            enterPressedTextField(email, password, email_lbl);
        }
    }
    @FXML
    public void enterPressedPassword(){
        enterPressedTextField(password,confirm_password,password_lbl);
    }
    @FXML
    public void enterPressedConfirmPassword(){
        confirm_password.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                if (!confirm_password.getText().equals(password.getText())) {
                    password_lbl.setText("*Password do not match");
                    confirm_password_lbl.setText("*Password do not match");
                    password_lbl.setVisible(true);
                    confirm_password_lbl.setVisible(true);
                } else {
                    password_lbl.setVisible(false);
                    confirm_password_lbl.setVisible(false);
                    try {
                        addMember();
                    }catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Exception in signUp() : " + e.getMessage());
                        System.out.println("Exception in signUp() : " + e.getCause());
                        System.out.println("Exception in signUp() : " + e.getLocalizedMessage());
                        e.getCause();
                    }
                }
            }
        });
    }

//store date

    @FXML
    private void addMember() {
        String userFirstName = first_name.getText();
        String userLastName = last_name.getText();
        String userAddress = address.getText();
        String userMobile = mobile.getText();
        String userEmail = email.getText();
        String userPassword = password.getText();

        if (userFirstName.isEmpty() || userLastName.isEmpty() || userAddress.isEmpty() || userMobile.isEmpty()||userEmail.isEmpty() || userPassword.isEmpty()) {
            if (userFirstName.isEmpty())
                first_name_lbl.setVisible(true);
            if (userLastName.isEmpty())
                last_name_lbl.setVisible(true);
            if (userAddress.isEmpty())
                address.setVisible(true);
            if (userMobile.isEmpty())
                mobile.setVisible(true);
            if(!userMobile.matches(regex)) {
                mobile_lbl.setText("*Invalid Mobile Phone number");
                mobile_lbl.setVisible(true);
            }
            if (userEmail.isEmpty())
                email_lbl.setVisible(true);
            if(!userEmail.matches(emailPattern)) {
                email_lbl.setText("*Invalid email address");
                email_lbl.setVisible(true);
            }
            if (userPassword.isEmpty())
                password_lbl.setVisible(true);
            if(confirm_password.getText().isEmpty())
                confirm_password_lbl.setVisible(true);
        }else {

            String registerMember = "INSERT INTO user_account (user_type,first_name,last_name,address,mobile,email,password,is_blocked) VALUES(" + "'user'," +
                    "'" + userFirstName + "'," +
                    "'" + userLastName + "'," +
                    "'" + userAddress + "'," +
                    "'" + userMobile + "'," +
                    "'" + userEmail + "'," +
                    "'" + userPassword + "'," +
                    "" + true + "" + ")";
            try {
                stmt = connectDB.prepareStatement(registerMember);
                stmt.executeUpdate(registerMember);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setContentText("New Member Registered Successfully");
                alert.showAndWait();
                cancel();
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Registration Failed");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void cancel (){
        Stage stage = (Stage) addMemberPane.getScene().getWindow();
        stage.close();
    }
}