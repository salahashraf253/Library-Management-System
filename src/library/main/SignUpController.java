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
import library.users.Member;
import library.users.User;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private AnchorPane signUp_pane;
    @FXML
    private TextField first_name;
    @FXML
    private TextField last_name;
    @FXML
    private Label first_name_lbl;
    @FXML
    private Label last_name_lbl;
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
    private PasswordField confirm_password;
    @FXML
    private Label confirm_password_lbl;
    @FXML
    private Button signUpBtn;
    @FXML
    private Button gotoLoginBtn;
    final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    final String regex = "^(?:01)[0-9]{9}$";

    Member m =new Member();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
                            signUp();
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

    @FXML
    public void signUp() {

        String userFirstName = first_name.getText();
        String userLastName = last_name.getText();
        String userAddress = address.getText();
        String userMobile = mobile.getText();
        String userEmail = email.getText();
        String userPassword = password.getText();


        if (userFirstName.isEmpty() || userLastName.isEmpty() || userAddress.isEmpty() || userMobile.isEmpty() || userEmail.isEmpty() || userPassword.isEmpty()){// || checkUserEmail(userEmail)) {
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
        } else {

            first_name_lbl.setVisible(false);
            last_name_lbl.setVisible(false);
            address_lbl.setVisible(false);
            mobile_lbl.setVisible(false);
            email_lbl.setVisible(false);
            password_lbl.setVisible(false);
            confirm_password_lbl.setVisible(false);

            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            PreparedStatement pst;

            String check = "SELECT count(1) FROM user_account WHERE email =?";

            String registerMember = "INSERT INTO libraryassistant.user_account (user_type,first_name, last_name, address, mobile, email, password,is_blocked) VALUES(" +
                    "'user'," + "'" + userFirstName + "'," + "'" + userLastName + "'," + "'" + userAddress + "'," + "'" + userMobile + "'," + "'" + userEmail + "'," + "'" + userPassword +"',"+true+""+")";

            try {
                Statement stmt = connectDB.createStatement();
                pst = connectDB.prepareStatement(check);
                pst.setString(1,userEmail);
                ResultSet rs = pst.executeQuery();

                while (rs.next()){
                    int count = ((ResultSet) rs).getInt(1);
                    System.out.println(count);
                    if (count>=1){
                        JOptionPane.showMessageDialog(null,"User already exists");
                    }
                    else {
                        System.out.println("No User Exists with that email");
                        try {
                            stmt.executeUpdate(registerMember);
                            System.out.println("User Registered Successfully");
                        }catch (Exception e){
                            e.printStackTrace();
                            e.getCause();
                        }
                        signUpBtn.disableProperty();
                        loadWindow("/library/main/Dashboard.fxml", "DashBoard", true);
                        closeWindow(signUp_pane);
                        }
                    try {
                        String getUserId = "SELECT user_id FROM user_account WHERE email =?";
                        PreparedStatement ps = connectDB.prepareStatement(getUserId);
                        ps.setString(1,userEmail);
                        Statement statement =connectDB.createStatement();
                        ResultSet rst = statement.executeQuery(getUserId);
                        User.currentId = rst.getInt("user_id");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    connectDB.close();
                    }
            }catch (Exception ex){
                ex.printStackTrace();
                ex.getCause();
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

   /* public static boolean isMemberExists(String userEmail) {
                try {
                    String checkstmt = "SELECT * FROM MEMBER WHERE email=?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);
            stmt.setString(1, userEmail);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = ((ResultSet) rs).getInt(1);
                System.out.println(count);

                return(count > 0);
            }
        } catch (SQLException ex) {
            System.out.printf("already exsist");
        }
        return false;
    }

    */
    /*public boolean checkUserEmail(String userEmail)
    {
        DatabaseHandler handler ;
        PreparedStatement ps;
        ResultSet rs;
        boolean checkUser = false;
        String query = "SELECT * FROM MEMBER WHERE email =?";

        try {
            ps = DatabaseHandler.getInstance().getConnection().prepareStatement(query);
            ps.setString(1, userEmail);

            rs = ps.executeQuery();

            if(rs.next())
            {
                checkUser = true;
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Failed");
                alert.showAndWait();

            }
        } catch (SQLException ex) {
            Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return checkUser;

        }
*/



}
