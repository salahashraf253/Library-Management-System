package library.books;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import library.database.DatabaseConnection;
import library.users.Member;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class Books {
    DatabaseConnection connectNow = new DatabaseConnection();
    Connection connectDB = connectNow.getConnection();
   /* String id;
    String title;
    String author;
    String publisher;
    Boolean isAvail;

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
    }*/
    private ImageView bookCover;
    private SimpleStringProperty title;
    private SimpleIntegerProperty bookId;
    private SimpleStringProperty author;
    private SimpleStringProperty publisher;
    private SimpleStringProperty category;
    private SimpleFloatProperty price;
    private SimpleStringProperty status;

    public Books(){}

    public Books(ImageView bookCover, String title, int bookId, String author, String publisher, String category, Float price, String status) {
        this.bookCover = bookCover;
        this.title = new SimpleStringProperty(title);
        this.bookId = new SimpleIntegerProperty(bookId);
        this.author = new SimpleStringProperty(author);
        this.publisher = new SimpleStringProperty(publisher);
        this.category = new SimpleStringProperty(category);
        this.price = new SimpleFloatProperty(price);
        this.status = new SimpleStringProperty(status);

    }

    public String getTitle() {
        return title.get();
    }

    public ImageView getBookCover() {
        return bookCover;
    }

    public int getBookId() {
        return bookId.get();
    }

    public String getAuthor() {
        return author.get();
    }

    public String getPublisher() {
        return publisher.get();
    }

    public String getCategory() {
        return category.get();
    }

    public Float getPrice() {
        return price.get();
    }

    public String getStatus() {
        return status.get();
    }

    public Boolean getBookAvailability(String bookId) {
        boolean bookStatus = false;
        String getStatus = "SELECT is_available FROM books WHERE book_id=?";
        try {
            PreparedStatement pst = connectDB.prepareStatement(getStatus);
            pst.setString(1,bookId);
            Statement stmt = connectDB.createStatement();
            ResultSet rs = stmt.executeQuery(getStatus);
            bookStatus = rs.getBoolean("is_available");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        if(bookStatus==true)
            return true;
        else
            return false;
    }

    public  void  addRentedBook(Books B, Member m, LocalDate rd, LocalDate cd){

       String QU="Insert INTO";

    }

    public void rentBook (Member m, Books b) throws IOException {
            if (b.getBookAvailability("0")) {
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
        this.status = status;
    }

    public void showRentedBooks(Member m, Books b) {

    }

    public void setBookAvailability(boolean b) {
    }

    public void search(String title){
        String searchBooks = "SELECT book_title  FROM books WHERE book_title like ?";
        try {
            PreparedStatement pst = connectDB.prepareStatement(searchBooks);
            pst.setString(1,"%"+title+"%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("book_title"));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

    }
    }

