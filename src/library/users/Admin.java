package library.users;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import library.books.Books;
import library.database.DatabaseConnection;

import java.lang.reflect.Member;
import java.sql.Connection;

public class Admin extends User{
    boolean adminStatus;
    DatabaseConnection connectNow = new DatabaseConnection();
    Connection connectDB = connectNow.getConnection();


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
    }
    public Admin(int id, String firstName, String lastName, String address, int mobile, String email) {
        this.id = new SimpleIntegerProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.address = new SimpleStringProperty(address);
        this.email = new SimpleStringProperty(email);
        this.mobile = new SimpleIntegerProperty(mobile);

    }
    public Admin() {
    }


    @Override
    void rent(Member m, Books B, String period) {

    }

}
