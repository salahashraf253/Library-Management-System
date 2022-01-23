package library.users;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import library.database.DatabaseConnection;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class UpdateMemberAccountController implements Initializable {
    final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    final String regex = "^(?:01)[0-9]{9}$";

    @FXML
    private JFXTextField first_name_txt;
    @FXML
    private JFXTextField last_name_txt;
    @FXML
    private JFXTextField address_txt;
    @FXML
    private JFXTextField mobile_input;
    @FXML
    private JFXTextField email_txt;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXPasswordField confirm_password;
    @FXML
    private Label first_name_lbl;
    @FXML
    private Label last_name_lbl;
    @FXML
    private Label address_lbl;
    @FXML
    private Label mobile_lbl;
    @FXML
    private Label email_lbl;
    @FXML
    private Label password_lbl;
    @FXML
    private Label confirm_password_lbl;
    @FXML
    private JFXButton update_btn;
    @FXML
    private JFXButton change_password_btn;

    DatabaseConnection connectNow = new DatabaseConnection();
    Connection connectDB = connectNow.getConnection();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    protected void update(){
        String btn_txt = null;
       switch (btn_txt)
       {
           case "Update":
               update_btn.setText("Submit");
               first_name_txt.setEditable(true);
               last_name_txt.setEditable(true);
               address_txt.setEditable(true);
               mobile_input.setEditable(true);
               email_txt.setEditable(true);
               change_password_btn.setVisible(true);
               break;
           case "Submit":
               updateMemberAccount();
               break;
           default:break;
       }
    }
    protected void showAccountInfo(int currentUserId){
        String viewInfo = "SELECT *  FROM user_account WHERE user_id='" + currentUserId+ "'";
        try {
            Statement stmt = connectDB.createStatement();
            ResultSet rs = stmt.executeQuery(viewInfo);
            while (true) {
                assert rs != null;
                if (!rs.next()) break;
                int id = rs.getInt("user_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String address = rs.getString("address");
                int mobile = rs.getInt("mobile");
                String email = rs.getString("email");
                first_name_txt.setText(firstName);
                last_name_txt.setText(lastName);
                address_txt.setText(address);
                mobile_input.setText(String.valueOf(mobile));
                email_txt.setText(email);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }


    }
    protected void updateMemberAccount(){
        first_name_txt.setEditable(true);
        last_name_txt.setEditable(true);
        address_txt.setEditable(true);
        mobile_input.setEditable(true);
        email_txt.setEditable(true);

        String userFirstName = first_name_txt.getText();
        String userLastName = last_name_txt.getText();
        String userAddress = address_txt.getText();
        String userMobile = mobile_input.getText();
        String userEmail = email_txt.getText();

        if (userFirstName.isEmpty() || userLastName.isEmpty() || userAddress.isEmpty() || userMobile.isEmpty()||userEmail.isEmpty() /*|| userPassword.isEmpty()*/) {
            if (userFirstName.isEmpty())
                first_name_lbl.setVisible(true);
            if (userLastName.isEmpty())
                last_name_lbl.setVisible(true);
            if (userAddress.isEmpty())
                address_lbl.setVisible(true);
            if (userMobile.isEmpty())
                mobile_lbl.setVisible(true);
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
           /* if (userPassword.isEmpty())
                password_lbl.setVisible(true);
            if(confirm_password.getText().isEmpty())
                confirm_password_lbl.setVisible(true);*/
        }else {

            String registerMember = "INSERT INTO user_account (user_type,first_name,last_name,address,mobile,email,password,is_blocked) VALUES(" + "'user'," +
                    "'" + userFirstName + "'," +
                    "'" + userLastName + "'," +
                    "'" + userAddress + "'," +
                    "'" + userMobile + "'," +
                    "'" + userEmail + "'," +

                    "" + true + "" + ")";
            try {
                Statement stmt = connectDB.prepareStatement(registerMember);
                stmt.executeUpdate(registerMember);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setContentText("Your Account has been Updated Successfully");
                alert.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Registration Failed");
                alert.showAndWait();
            }
        }
    }

    public void changePassword() {
        password.setEditable(true);
        confirm_password.setVisible(true);

    }
}
