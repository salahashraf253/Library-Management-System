package library.users;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import library.books.AddBookController;
import library.books.Books;
import library.database.DatabaseConnection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Member extends User {
    boolean memberStatus;
    DatabaseConnection connectNow = new DatabaseConnection();
    Connection connectDB = connectNow.getConnection();

    public Member(int id, String firstName, String lastName, String address, int phone, String email, String password, boolean isBlocked) {
        super();
        this.id = new SimpleIntegerProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.address = new SimpleStringProperty(address);
        this.email = new SimpleStringProperty(email);
        this.mobile = new SimpleIntegerProperty(phone);
        this.password = new SimpleStringProperty(password);
        this.isBlocked= new SimpleBooleanProperty(isBlocked);
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

    public Boolean getMemberStatus(){
        String status = "SELECT is_blocked FROM user_account where user_id=?";
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
        if(memberStatus==true)
            return true;
        else
            return false;
    }

    void add_book(Books B ,Member m){
        // add book from data base;

    }


    public void search(String name) {
        String searchMember = "SELECT user_id FROM user_account WHERE user_type='user' AND (first_name LIKE ? OR last_name LIKE ?)";
        try {
            PreparedStatement stmt = connectDB.prepareStatement(searchMember);
            stmt.setString(1,"%"+name+"%");
            stmt.setString(2,"%"+name+"%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String userId = rs.getString("user_id");
                System.out.println(userId);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE, null, throwable);
        }

    }


    @Override
    void rent(java.lang.reflect.Member m, Books B, String period) {
    }
}

