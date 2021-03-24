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
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
    private TableColumn<BookOrderList, JFXButton> return_book_col;

    ObservableList<BookOrderList> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initCol();
        loadData();
    }

    private void initCol() {
        book_cover_col.setCellValueFactory(new PropertyValueFactory<>("bookCover"));
        title_col.setCellValueFactory(new PropertyValueFactory<>("title"));
        book_id_col.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        rent_date_col.setCellValueFactory(new PropertyValueFactory<>("rentDate"));
        return_date_col.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        return_book_col.setCellValueFactory(new PropertyValueFactory<>("returnBookBtn"));
    }
    public void loadData() {
        list.clear();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String viewRentedBooks = "SELECT *  FROM rent_book WHERE member_id= '"+ User.currentId+"'";
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
                    Date rent_Date = rs.getDate("rent_date");
                    Date return_Date = rs.getDate("return_date");
                    //Getting the default zone id
                    ZoneId defaultZoneId = ZoneId.systemDefault();
                    //Converting the date to Instant
                    Instant instant = rent_Date.toInstant();
                    Instant instant1 =return_Date.toInstant();
                    //Converting the Date to LocalDate
                    LocalDate rentDate = instant.atZone(defaultZoneId).toLocalDate();
                    LocalDate returnDate = instant1.atZone(defaultZoneId).toLocalDate();
                    try {
                        PreparedStatement pst = connectDB.prepareStatement(getBooks);
                        pst.setString(1,bookId);
                        ResultSet rst = pst.executeQuery();
                        if (rst.next()){
                            title = rs.getString("book_title");
                            cover = rs.getBlob("cover");

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

                    list.add(new BookOrderList(bookCover,title,bookId,rentDate,returnDate));
                }
            }catch (SQLException ex){
                ex.printStackTrace();
            }

        rented_books_table.getItems().setAll(list);
    }
}
