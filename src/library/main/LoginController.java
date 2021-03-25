package library.main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import library.database.DatabaseConnection;
import library.users.User;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
    private Button gotoSignUpBtn;
    public String userType;

    DatabaseConnection connectNow = new DatabaseConnection();
    Connection connectDB = connectNow.getConnection();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public void enterPressedTextField(TextField txt, PasswordField pass, Label lbl) {
        txt.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER) || ke.getCode().equals(KeyCode.DOWN)) {
                if (txt.getText().isEmpty()) {
                    lbl.setVisible(true);
                } else {
                    lbl.setVisible(false);
                    pass.requestFocus();
                }
            }
        });
    }

    @FXML
    public void enterPressedEmail() {
        enterPressedTextField(email, password, email_lbl);
    }

    @FXML
    public void enterPressedPassword() {
        password.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {login();}});
    }

    @FXML
    public void login() {
        String userEmail = email.getText();
        String userPassword = password.getText();
        if (userEmail.isEmpty() || userPassword.isEmpty()) {
            if (userEmail.isEmpty())
                email_lbl.setVisible(true);
            if (userPassword.isEmpty())
                password_lbl.setVisible(true);
        } else {
            email_lbl.setVisible(false);
            password_lbl.setVisible(false);
            validateLogin();
            try {
                String getUserId = "SELECT user_id,user_type FROM user_account WHERE email =?";
                PreparedStatement ps = connectDB.prepareStatement(getUserId);
                ps.setString(1,userEmail);
                ResultSet rst = ps.executeQuery();
                while (rst.next()) {
                    User.currentId = rst.getInt("user_id");
                    User.currentUserType = rst.getString("user_type");
                    System.out.println(User.currentId);
                    System.out.println(User.currentUserType);// To be deleted
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void validateLogin(){
        String verifyLogin = "SELECT count(1) FROM user_account WHERE email = '" + email.getText() + "' AND password = '" + password.getText() + "'";
        String getUserType = "SELECT user_type FROM user_account WHERE email = '" + email.getText()+ "'";
        try {
            Statement stmt = connectDB.createStatement();
            ResultSet rs = stmt.executeQuery(verifyLogin);
            while (rs.next()){
                if (rs.getInt(1) == 1){
                    try {
                        Statement statement = connectDB.createStatement();
                        ResultSet rst = statement.executeQuery(getUserType);
                        while (rst.next()){
                            userType = rst.getString("user_type");
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (userType.equals("admin"))
                        loadWindow("/library/main/AdminDashboard.fxml", "DashBoard", true);
                    else if (userType.equals("user"))
                        loadWindow("/library/main/Dashboard.fxml", "DashBoard", true);
                    closeWindow(login_pane);
                }
                else {
                    email_lbl.setText("*Incorrect Email");
                    email_lbl.setVisible(true);
                    password_lbl.setText("*Incorrect Password");
                    password_lbl.setVisible(true);
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
            ex.getCause();
        }
    }

    @FXML
    void goToSignUp() {
        loadWindow("/library/main/SignUp.fxml", "SignUp", false);
        closeWindow(login_pane);
    }

    void loadWindow(String loc, String title, Boolean max) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.setMaximized(max);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void closeWindow(AnchorPane pane) {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }
}