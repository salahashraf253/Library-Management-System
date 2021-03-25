package library.users;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import library.database.DatabaseConnection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

abstract public class User {
    protected SimpleIntegerProperty id;
    protected SimpleStringProperty firstName;
    protected SimpleStringProperty lastName;
    protected SimpleIntegerProperty mobile;
    protected SimpleStringProperty address;
    protected SimpleStringProperty email;
    protected SimpleStringProperty password;
    protected SimpleStringProperty isBlocked;

    public static int currentId;
    public static String currentUserType;

    DatabaseConnection connectNow = new DatabaseConnection();
    Connection connectDB = connectNow.getConnection();

    public void setId(int id) {
        this.id.set(id);
    }

    public int getId() {
        return id.get();
    }

    public String getFirstName() {
        return firstName.get();
    }

    public String getLastName() {
        return lastName.get();
    }

    public int getMobile() {
        return mobile.get();
    }

    public String getAddress() {
        return address.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getPassword() {
        return password.get();
    }

    public String isIsBlocked() {
        return isBlocked.get();
    }

    public abstract void removeUser(int userId);
    public  void blockUser(int userId){
        String block = "UPDATE user_account SET is_blocked = TRUE  WHERE user_id=?";
        JOptionPane.showConfirmDialog(null,"Are you sure to Block this User","BlocK User",JOptionPane.OK_CANCEL_OPTION);
        int check =0 ;
        if(check == JOptionPane.OK_OPTION) {
            try {
                PreparedStatement pst = connectDB.prepareStatement(block);
                pst.setInt(1, userId);
                pst.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            JOptionPane.showMessageDialog(null,"That's better!");
        }
    }
    public  void unblockUser(int userId){
        String block = "UPDATE user_account SET is_blocked = FALSE  WHERE user_id=?";
        JOptionPane.showConfirmDialog(null,"Are you sure to Unblock this user","Unblock User",JOptionPane.OK_CANCEL_OPTION);
        int check =0 ;
        if(check == JOptionPane.OK_OPTION) {
            try {
                PreparedStatement pst = connectDB.prepareStatement(block);
                pst.setInt(1, userId);
                pst.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {}
    }
}