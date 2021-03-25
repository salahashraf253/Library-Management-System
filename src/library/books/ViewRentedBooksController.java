package library.books;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import library.database.DatabaseConnection;
import library.users.User;

import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

public class ViewRentedBooksController implements Initializable {
    @FXML
    private AnchorPane viewRentedBooksPane;
    @FXML
    private JFXButton view_books_refresh_btn;
    @FXML
    private TableView rented_books_table;
    @FXML
    private TableColumn<BookOrderList, Image> book_cover_col;
    @FXML
    private TableColumn<BookOrderList, String> title_col;
    @FXML
    private TableColumn<BookOrderList, String> book_id_col;
    @FXML
    private TableColumn<BookOrderList, LocalDate> rent_date_col;
    @FXML
    private TableColumn<BookOrderList, LocalDate> return_date_col;
    @FXML
    private TableColumn<BookOrderList, String> remaining_time_col;
    @FXML
    private TableColumn<BookOrderList, JFXButton> return_book_col;

    ObservableList<BookOrderList> list = FXCollections.observableArrayList();

    private void initCol() {
        book_cover_col.setCellValueFactory(new PropertyValueFactory<>("bookCover"));
        title_col.setCellValueFactory(new PropertyValueFactory<>("title"));
        book_id_col.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        rent_date_col.setCellValueFactory(new PropertyValueFactory<>("rentDate"));
        return_date_col.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        remaining_time_col.setCellValueFactory(new PropertyValueFactory<>("remainingTime"));
        return_book_col.setCellValueFactory(new PropertyValueFactory<>("returnBookBtn"));
    }
    public void loadData() {
        list.clear();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String viewRentedBooks = "SELECT *  FROM rent_book WHERE member_id ='"+User.currentId+"' AND request_status= true";
        String getBooks = "SELECT book_title,cover FROM books WHERE book_id=?";
        String title= null;
        Blob cover = null;
        ImageView bookCover = null;
        try {
            Statement stmt = connectDB.createStatement();
            ResultSet rs = stmt.executeQuery(viewRentedBooks);
            while (true){
                assert rs != null;
                if (!rs.next()) break;
                String bookId= rs.getString("rented_book_id");
                String rent_Date = rs.getString("rent_date");
                String return_Date = rs.getString("delivery_date");
                //Converting the String to LocalDate
                LocalDate rentDate = LocalDate.parse(rent_Date);
                LocalDate returnDate = LocalDate.parse(return_Date);
                //Calculation of rentTime & remainingTime for every loaned
                long remainingTimePeriod = ChronoUnit.DAYS.between(LocalDate.now(),returnDate);
                String remainingTime = remainingTimePeriod+" (Days)";
                try {
                    PreparedStatement pst = connectDB.prepareStatement(getBooks);
                    pst.setString(1,bookId);
                    ResultSet rst = pst.executeQuery();
                    if (rst.next()){
                        title = rst.getString("book_title");
                        cover = rst.getBlob("cover");

                        if (cover==null){
                            Image image = new Image("Images/null_book.png");
                            bookCover = new ImageView();
                            bookCover.setImage(image);
                            bookCover.setFitWidth(70);
                            bookCover.setFitHeight(90);
                        }else {
                            try {
                                InputStream inputStream = cover.getBinaryStream();
                                Image image = new Image(inputStream);
                                bookCover = new ImageView();
                                bookCover.setImage(image);
                                bookCover.setFitWidth(70);
                                bookCover.setFitHeight(90);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                list.add(new BookOrderList(bookCover, title, bookId,User.currentId,rentDate,returnDate,null,remainingTime));
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        rented_books_table.getItems().setAll(list);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initCol();
        loadData();
    }
}
