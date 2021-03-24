package library.books;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
public class BookOrderList implements Initializable {
    private ImageView bookCover;
    private SimpleStringProperty title;
    private SimpleStringProperty bookId;
    private LocalDate rentDate;
    private LocalDate returnDate;
    private JFXButton returnBookBtn;

    Books book = new Books();
    public BookOrderList(ImageView bookCover, String title, String bookId, LocalDate rentDate, LocalDate returnDate) {
        this.bookCover = bookCover;
        this.title = new SimpleStringProperty(title);
        this.bookId = new SimpleStringProperty(bookId);
        this.rentDate = rentDate;
        this.returnDate = returnDate;
        this.returnBookBtn=new JFXButton("Return Book");
        this.returnBookBtn.setOnAction(event -> {book.returnBook(bookId);});
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
