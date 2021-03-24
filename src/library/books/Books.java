package library.books;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import library.database.DatabaseConnection;
import library.users.Member;
import library.users.User;

import java.sql.*;
import java.time.LocalDate;

public class Books {
    DatabaseConnection connectNow = new DatabaseConnection();
    Connection connectDB = connectNow.getConnection();

    private ImageView bookCover;
    private SimpleStringProperty title;
    private SimpleStringProperty bookId;
    private SimpleStringProperty author;
    private SimpleStringProperty publisher;
    private SimpleStringProperty category;
    private SimpleFloatProperty price;
    private SimpleStringProperty status;
    private JFXButton rentBookBtn;
    private JFXButton removeBookBtn;
    private JFXButton orderBookBtn;

    private AnchorPane anchorPane = new AnchorPane();
    private Label lbl = new Label("Enter Return Date");
    private DatePicker datePicker = new DatePicker();
    private JFXButton submit = new JFXButton("Submit");
    private JFXButton cancel = new JFXButton("Cancel");
    private Tooltip tooltip = new Tooltip();

    Member member = new Member();
    public Books(){}
    public Books(ImageView bookCover, String title, String bookId, String author, String publisher, String category, Float price, String status) {
        this.bookCover = bookCover;
        this.title = new SimpleStringProperty(title);
        this.bookId = new SimpleStringProperty(bookId);
        this.author = new SimpleStringProperty(author);
        this.publisher = new SimpleStringProperty(publisher);
        this.category = new SimpleStringProperty(category);
        this.price = new SimpleFloatProperty(price);
        this.status = new SimpleStringProperty(status);
        this.rentBookBtn = new JFXButton("Rent Book");
        this.rentBookBtn.setOnAction(e -> {rentBook();});
        this.removeBookBtn = new JFXButton("Remove Book");
        this.removeBookBtn.setOnAction(e -> {removeBook(bookId);});
        this.orderBookBtn = new JFXButton("Order Book");
        this.orderBookBtn.setOnAction(e -> {rentBook();});
    }

    public String getTitle() {
        return title.get();
    }
    public ImageView getBookCover() {
        return bookCover;
    }
    public String getBookId() {
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
    public JFXButton getRentBookBtn() {
        return rentBookBtn;
    }
    public void setRentBookBtn(JFXButton rentBookBtn) {
        this.rentBookBtn = rentBookBtn;
    }
    public JFXButton getRemoveBookBtn() {
        return removeBookBtn;
    }
    public void setRemoveBookBtn(JFXButton removeBookBtn) {
        this.removeBookBtn = removeBookBtn;
    }
    public JFXButton getOrderBookBtn() {
        return orderBookBtn;
    }
    public void setOrderBookBtn(JFXButton orderBookBtn) {
        this.orderBookBtn = orderBookBtn;
    }
    public void setIsAvail(Boolean isAvail) {
        this.status = status;
    }

    public Boolean checkBookAvailability(String bookId) {
        boolean bookStatus = false;
        String getStatus = "SELECT is_available FROM books WHERE book_id=?";
        try {
            PreparedStatement pst = connectDB.prepareStatement(getStatus);
            pst.setString(1,bookId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                bookStatus = rs.getBoolean("is_available");
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return bookStatus;
    }

    public  void  addRentedBook(LocalDate returnDate){
        int memberId = User.currentId;
        LocalDate rentDate = LocalDate.now();
        String rentedBookId = bookId.getValue();
        try {
            Statement stmt = connectDB.createStatement();
            Statement st = connectDB.createStatement();
            Statement s = connectDB.createStatement();
            String bookRented = "INSERT INTO rent_book(rented_book_id,member_id,rent_date,delivery_date) VALUES("+
                    "'" + rentedBookId + "'," +
                    "'" + memberId + "'," +
                    "'" + rentDate + "'," +
                    "'" + returnDate + "')";
            String updateBookStatus = "UPDATE books SET is_available = false WHERE book_id ='"+ rentedBookId +"'";
            String updateMemberRentFlag = "UPDATE user_account SET renting_book = TRUE WHERE user_id ='"+ User.currentId +"'";
            stmt.executeUpdate(bookRented);
            st.executeUpdate(updateBookStatus);
            s.executeUpdate(updateMemberRentFlag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dateChooser(){
        Stage stage;
        anchorPane.setPrefWidth(270);
        anchorPane.setPrefHeight(150);
        anchorPane.getChildren().addAll(lbl,datePicker,cancel,submit);
        anchorPane.setVisible(true);
        lbl.setLayoutX(80);
        lbl.setLayoutY(10);
        datePicker.setLayoutX(48);
        datePicker.setLayoutY(40);
        submit.setLayoutX(152);
        submit.setLayoutY(104);
        submit.setTooltip(tooltip);
        cancel.setLayoutX(47);
        cancel.setLayoutY(104);
        cancel.setTooltip(tooltip);
        tooltip.setStyle("-fx-background-color: #bdbdbd;");
        submit.setOnAction(event -> {
            if (datePicker!=null) {
                LocalDate returnDate = datePicker.getValue();
                if (returnDate != null) {
                    addRentedBook(returnDate);
                    close(anchorPane);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Return Date cannot be Empty");
                    alert.showAndWait();
                }
            }
        });
        cancel.setOnAction(event -> {
            close(anchorPane);
        });
        stage = new Stage(StageStyle.UNDECORATED);
        stage.setScene(new Scene(anchorPane));
        stage.show();
    }

    public void rentBook () {
        if (checkBookAvailability(bookId.getValue()) && member.getMemberStatus()) {
            dateChooser();
        } else {
            if (!checkBookAvailability(bookId.getValue())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("this book is Unavailable");
                alert.showAndWait();
            } else if (!member.getMemberStatus()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Sorry You Cannot Rent This Book");
                alert.showAndWait();
            }
        }
    }

    public void showRentedBooks(String memberId, String bookId) {
    }

    public void returnBook(String rentedBookId) {
        int memberId = User.currentId;
        try {
            Statement stmt = connectDB.createStatement();
            Statement st = connectDB.createStatement();
            String bookRented = "DELETE FROM rent_book WHERE (rented_book_id,member_id) IN("+
                    "'" + rentedBookId + "'," +
                    "'" + memberId + "')";
            String updateBookStatus = "UPDATE books SET is_available = true WHERE book_id ='"+ rentedBookId +"'";
            stmt.executeUpdate(bookRented);
            st.executeUpdate(updateBookStatus);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeBook(String bookId){
        String checkRentedBook = "SELECT member_id FROM rent_book WHERE rented_book_id = '" + bookId +"'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet rs = statement.executeQuery(checkRentedBook);
            if (rs.next()){
                int memberId = rs.getInt("member_id");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("User " + memberId + " is renting a book right now! Please wait until returning it");
                    alert.showAndWait();
                }else {
                    try {
                        String deleteMember ="DELETE FROM books WHERE book_id=?";
                        PreparedStatement stmt= connectDB.prepareStatement(deleteMember);
                        stmt.setString(1,bookId);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setBookAvailability(boolean b) {
    }

    public void close(AnchorPane anchorPane){
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }
    }

