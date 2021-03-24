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

import javax.swing.*;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ViewBooksController implements Initializable {
    @FXML
    private AnchorPane viewBooksPane;
    @FXML
    private AnchorPane title_pane;
    @FXML
    private JFXButton view_books_back_btn;
    @FXML
    private JFXButton view_books_refresh_btn;
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
    @FXML
    private TableColumn<Books, JFXButton> rentCol;
    ObservableList<Books> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initCol();
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
        rentCol.setCellValueFactory(new PropertyValueFactory<>("rentBookBtn"));
    }
    public void loadData(String btnId,String searchTxt) {
        list.clear();
        String availability;
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String viewBooks = "SELECT *  FROM books";
        String searchBooks = "SELECT * FROM books WHERE book_title like ?";
        Statement stmt = null;
        try {
            PreparedStatement pst = connectDB.prepareStatement(searchBooks);
            pst.setString(1,"%"+searchTxt+"%");
            stmt = connectDB.createStatement();
            ResultSet rs=null;
            if(btnId.equals("viewBooksBtn")){
                rs = stmt.executeQuery(viewBooks);
            }else if(btnId.equals("searchBookBtn")){
                rs= pst.executeQuery();
            }else {
                JOptionPane.showMessageDialog(null,"No Book Found");
            }
            try {
                while (true){
                    assert rs != null;
                    if (!rs.next()) break;
                    String title = rs.getString("book_title");
                    String bookId= rs.getString("book_id");
                    String author = rs.getString("author");
                    String publisher = rs.getString("publisher");
                    String category = rs.getString("category");
                    Float price = rs.getFloat("price");
                    Boolean isAvail = rs.getBoolean("is_available");
                    if (isAvail)
                        availability = "Available";
                    else
                        availability= "Unavailable";
                    Blob cover = rs.getBlob("cover");
                    ImageView bookCover = null;
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
    public void back() {
        viewBooksPane.setVisible(false);
    }
    public void refresh() {
        loadData("viewBooksBtn",null);
    }
}
