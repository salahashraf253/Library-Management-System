package library.books;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import library.database.DatabaseConnection;

import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
public class BookOrderList implements Initializable {
    private ImageView bookCover;
    private SimpleStringProperty title;
    private SimpleStringProperty bookId;
    private SimpleIntegerProperty memberId;
    private LocalDate rentDate;
    private LocalDate returnDate;
    private JFXButton returnBookBtn;
    private JFXButton approveBtn;
    private JFXButton declineBtn;
    private SimpleStringProperty rentTime;
    private SimpleStringProperty remainingTime;

    DatabaseConnection connectNow = new DatabaseConnection();
    Connection connectDB = connectNow.getConnection();

    Books book = new Books();
    public BookOrderList(ImageView bookCover, String title, String bookId,int memberId, LocalDate rentDate, LocalDate returnDate,String rentTime,String remainingTime) {
        this.bookCover = bookCover;
        this.title = new SimpleStringProperty(title);
        this.bookId = new SimpleStringProperty(bookId);
        this.memberId = new SimpleIntegerProperty(memberId);
        this.rentDate = rentDate;
        this.returnDate = returnDate;
        this.returnBookBtn=new JFXButton("Return Book");
        this.returnBookBtn.setOnAction(event -> {book.returnBook(bookId);});
        this.approveBtn=new JFXButton("Approve");
        this.approveBtn.setOnAction(event -> {approveRequest(bookId,memberId);});
        this.declineBtn=new JFXButton("Decline");
        this.declineBtn.setOnAction(event -> {declineRequest(bookId,memberId);});
        this.rentTime = new SimpleStringProperty(rentTime);
        this.remainingTime = new SimpleStringProperty(remainingTime);
    }

    public void setBookCover(ImageView bookCover) {
        this.bookCover = bookCover;
    }
    public void setTitle(String title) {
        this.title.set(title);
    }
    public void setBookId(String bookId) {
        this.bookId.set(bookId);
    }
    public void setRentDate(LocalDate rentDate) {
        this.rentDate = rentDate;
    }
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
    public void setReturnBookBtn(JFXButton returnBookBtn) {
        this.returnBookBtn = returnBookBtn;
    }
    public ImageView getBookCover() {
        return bookCover;
    }
    public String getTitle() {
        return title.get();
    }
    public SimpleStringProperty titleProperty() {
        return title;
    }
    public String getBookId() {
        return bookId.get();
    }
    public SimpleStringProperty bookIdProperty() {
        return bookId;
    }
    public LocalDate getRentDate() {
        return rentDate;
    }
    public LocalDate getReturnDate() {
        return returnDate;
    }
    public JFXButton getReturnBookBtn() {
        return returnBookBtn;
    }
    public void setMemberId(int memberId) {
        this.memberId.set(memberId);
    }
    public int getMemberId() {
        return memberId.get();
    }
    public SimpleIntegerProperty memberIdProperty() {
        return memberId;
    }
    public JFXButton getApproveBtn() {
        return approveBtn;
    }
    public JFXButton getDeclineBtn() {
        return declineBtn;
    }
    public void setApproveBtn(JFXButton approveBtn) {
        this.approveBtn = approveBtn;
    }
    public void setDeclineBtn(JFXButton declineBtn) {
        this.declineBtn = declineBtn;
    }
    public void setRentTime(String rentTime) {
        this.rentTime.set(rentTime);
    }
    public void setRemainingTime(String remainingTime) { this.remainingTime.set(remainingTime);}
    public String getRentTime() {
        return rentTime.get();
    }
    public SimpleStringProperty rentTimeProperty() {
        return rentTime;
    }
    public String getRemainingTime() {
        return remainingTime.get();
    }
    public SimpleStringProperty remainingTimeProperty() {
        return remainingTime;
    }

    public void approveRequest(String bookId, int memberId){
        String approve = "UPDATE rent_book SET request_status = TRUE WHERE rented_book_id='"+bookId+"'AND member_id = '"+memberId+"'";
        try {
            Statement stmt = connectDB.createStatement();
            stmt.executeUpdate(approve);
            declineBtn.setDisable(true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void declineRequest(String bookId, int memberId){
        String decline = "UPDATE rent_book SET request_status = FALSE WHERE rented_book_id='"+bookId+"'AND member_id = '"+memberId+"'";
        try {
            Statement stmt = connectDB.createStatement();
            stmt.executeUpdate(decline);
            approveBtn.setDisable(true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
