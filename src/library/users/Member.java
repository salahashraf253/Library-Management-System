package library.users;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import library.database.DatabaseConnection;

import java.sql.*;

public class Member extends User {
    private JFXButton removeMemberBtn;
    private JFXButton blockMemberBtn;

    DatabaseConnection connectNow = new DatabaseConnection();
    Connection connectDB = connectNow.getConnection();

    public Member(int id, String firstName, String lastName, String address, int phone, String email, String memberStatus) {
        super();
        this.id = new SimpleIntegerProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.address = new SimpleStringProperty(address);
        this.email = new SimpleStringProperty(email);
        this.mobile = new SimpleIntegerProperty(phone);
        this.isBlocked= new SimpleStringProperty(memberStatus);
        this.removeMemberBtn = new JFXButton("Remove Member");
        this.removeMemberBtn.setOnAction(e -> {removeUser(id);});
        this.blockMemberBtn = new JFXButton("Block Member");
        if (memberStatus.equals("Blocked")){
            this.blockMemberBtn.setText("Unblock Member");
            this.blockMemberBtn.setOnAction(e -> {unblockUser(id);});
        }
        else {
            this.blockMemberBtn.setOnAction(e -> {
                blockUser(id);
            });
        }
    }
    public Member(int id, String firstName, String lastName, String address, int mobile, String email) {
        this.id = new SimpleIntegerProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.address = new SimpleStringProperty(address);
        this.email = new SimpleStringProperty(email);
        this.mobile = new SimpleIntegerProperty(mobile);
    }
    public Member() {
    }

    public void setRemoveMemberBtn(JFXButton removeMemberBtn) {
        this.removeMemberBtn = removeMemberBtn;
    }
    public void setBlockMemberBtn(JFXButton blockMemberBtn) {
        this.blockMemberBtn = blockMemberBtn;
    }
    public JFXButton getRemoveMemberBtn() {
        return removeMemberBtn;
    }
    public JFXButton getBlockMemberBtn() {
        return blockMemberBtn;
    }

    public Boolean getMemberStatus(){
        String status = "SELECT is_blocked FROM user_account where user_type='user'AND user_id =?";
        boolean memberStatus = false;
        try {
            PreparedStatement pst = connectDB.prepareStatement(status);
           pst.setInt(1,currentId);
           ResultSet rs = pst.executeQuery();
           if (rs.next()){
              memberStatus = rs.getBoolean("is_blocked");
           }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(memberStatus==false)
            return true;
        else
            return false;
    }

    @Override
    public void removeUser(int memberId){
        String checkRentFlag = "SELECT renting_book FROM user_account WHERE user_id = '" + memberId +"'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet rs = statement.executeQuery(checkRentFlag);
            if (rs.next()){
                boolean rentingBook = rs.getBoolean("renting_book");
                if (rentingBook){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("User is renting a book right now! Please wait until returning it");
                    alert.showAndWait();
                }else {
                    try {
                        String deleteMember ="DELETE FROM User_account WHERE user_id=?";
                        PreparedStatement stmt= connectDB.prepareStatement(deleteMember);
                        stmt.setInt(1,memberId);
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

