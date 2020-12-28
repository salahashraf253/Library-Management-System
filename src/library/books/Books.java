package library.books;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import library.main.DashboardController;
import library.users.Member;
import library.database.Libdb;

import java.io.IOException;
import java.sql.ResultSet;
import java.time.LocalDate;

public class Books {
    String id;
    String title;
    String author;
    String publisher;
    Boolean isAvail;
    Libdb libdb = new Libdb();
    public Books(String id, String title, String author, String publisher, Boolean isAvail) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.isAvail = isAvail;
    }

    public Books() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Boolean getBookAvailability() {
     /*   String q = "SELECT status FROM Book where ";
        ResultSet rs = libdb.execQuery(q);
        // we want to check if Book status is tru or false.........................
        if(rs){
            return true;
        }else{
            return false;
        }*/return true;
    }

    public  void  addRentedBook(Books B, Member m, LocalDate rd, LocalDate cd){


       String QU="Insert INTO";
        ResultSet R= libdb.execQuery(QU);


    }

    public void rentBook (Member m, Books b) throws IOException {
            if (b.getBookAvailability()) {
                Parent root = FXMLLoader.load(getClass().getResource("ReturnDate.fxml"));
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("this book is Unavailable");
                alert.showAndWait();
            }

    }


    public void setIsAvail(Boolean isAvail) {
        this.isAvail = isAvail;
    }

    public void showRentedBooks(Member m, Books b) {

    }

    public void setBookAvailability(boolean b) {
    }
}
