package library.users;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import library.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Admin extends User{
    DatabaseConnection connectNow = new DatabaseConnection();
    Connection connectDB = connectNow.getConnection();

    private JFXButton removeAdminBtn;
    private JFXButton blockAdminBtn;

    public JFXButton getRemoveAdminBtn() {
        return removeAdminBtn;
    }
    public JFXButton getBlockAdminBtn() {
        return blockAdminBtn;
    }
    public void setRemoveAdminBtn(JFXButton removeAdminBtn) {
        this.removeAdminBtn = removeAdminBtn;
    }
    public void setBlockAdminBtn(JFXButton blockAdminBtn) {
        this.blockAdminBtn = blockAdminBtn;
    }

    public Admin(int id, String firstName, String lastName, String address, int phone, String email, String password, String isBlocked) {
        super();
        this.id = new SimpleIntegerProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.address = new SimpleStringProperty(address);
        this.email = new SimpleStringProperty(email);
        this.mobile = new SimpleIntegerProperty(phone);
        this.password = new SimpleStringProperty(password);
        this.isBlocked= new SimpleStringProperty(isBlocked);
        this.removeAdminBtn = new JFXButton("Remove Member");
        this.removeAdminBtn.setOnAction(e -> {removeUser(id);});
        this.blockAdminBtn = new JFXButton("Block Member");
        if (isBlocked.equals("Blocked")){
            this.blockAdminBtn.setText("Unblock Member");
            this.blockAdminBtn.setOnAction(e -> {unblockUser(id);});
        }
        else {
            this.blockAdminBtn.setOnAction(e -> {
                blockUser(id);
            });
        }
    }
    public Admin() {
    }

    public void removeUser(int adminId){
        String checkRentFlag = "SELECT renting_book FROM user_account WHERE user_id = '" + adminId +"'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet rs = statement.executeQuery(checkRentFlag);
            if (rs.next()){
                boolean rentingBook = rs.getBoolean("renting_book");
                if (rentingBook){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Admin is renting a book right now! Please wait until returning it");
                    alert.showAndWait();
                }else {
                    try {
                        String deleteMember ="DELETE FROM User_account WHERE user_id=?";
                        PreparedStatement stmt= connectDB.prepareStatement(deleteMember);
                        stmt.setInt(1,adminId);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
