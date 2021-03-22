package library.books;

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

import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ViewBooksController implements Initializable {
    @FXML
    private AnchorPane viewBooksPane;
    @FXML
    private TableView booksTable;
    @FXML
    private TableColumn<Books, Image> photoCol;
    @FXML
    private TableColumn<Books, String> titleCol;
    @FXML
    private TableColumn<Books, String> idCol;
    @FXML
    private TableColumn<Books, String> authorCol;
    @FXML
    private TableColumn<Books, String> publisherCol;
    @FXML
    private TableColumn<Books, String> categoryCol;
    @FXML
    private TableColumn<Books, Float> priceCol;
    @FXML
    private TableColumn<Books, Boolean> statusCol;

    ObservableList<Books> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initCol();
        loadData();
    }

    private void initCol() {
        photoCol.setCellValueFactory(new PropertyValueFactory<>("bookCover"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
    private void loadData() {
        String availability;
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String qu = "SELECT *  FROM books";
        Statement stmt = null;
        try {
            stmt = connectDB.createStatement();
            ResultSet rs = stmt.executeQuery(qu);
            try {
                while (rs.next()){
                    String title = rs.getString("book_title");
                    int bookId= rs.getInt("book_id");
                    String author = rs.getString("author");
                    String publisher = rs.getString("publisher");
                    String category = rs.getString("category");
                    Float price = rs.getFloat("price");
                    Boolean isAvail = rs.getBoolean("is_available");
                    if (isAvail==true)
                        availability = "Available";
                    else
                        availability= "Unavailable";
                    Blob cover = rs.getBlob("cover");
                    ImageView bookCover = null;
                    try {
                        InputStream inputStream = cover.getBinaryStream();
                        Image image = new Image(inputStream);
                        bookCover = new ImageView();
                        bookCover.setImage(image);
                        bookCover.setFitWidth(70);
                        bookCover.setFitHeight(90);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    list.add(new Books(bookCover,title,bookId,author,publisher,category,price,availability));
                }
            }catch (SQLException ex){
               ex.printStackTrace();
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        booksTable.getItems().setAll(list);
    }
}
