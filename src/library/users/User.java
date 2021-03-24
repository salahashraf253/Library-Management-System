package library.users;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import library.books.Books;

import java.lang.reflect.Member;

abstract public class User {
    protected SimpleIntegerProperty id;
    protected SimpleStringProperty firstName;
    protected SimpleStringProperty lastName;
    protected SimpleIntegerProperty mobile;
    protected SimpleStringProperty address;
    protected SimpleStringProperty email;
    protected SimpleStringProperty password;
    protected SimpleStringProperty isBlocked;

    protected String type;
    public static int currentId;
    public static String currentUserType;

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

    public String getType() {
        return type;
    }

    abstract void rent(Member m, Books B,String period);


}