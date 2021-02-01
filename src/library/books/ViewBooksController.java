package library.books;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import library.database.DatabaseHandler;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewBooksController implements Initializable {

    @FXML
    private AnchorPane viewBooksPane;
    @FXML
    private TableView booksTable;
    @FXML
    private TableColumn<Book, Image> photoCol;
    @FXML
    private TableColumn<Book, String> titleCol;
    @FXML
    private TableColumn<Book, String> authorCol;
    @FXML
    private TableColumn<Book, String> publisherCol;
    @FXML
    private TableColumn<Book, String> categoryCol;
    @FXML
    private TableColumn<Book, Float> priceCol;
    @FXML
    private TableColumn<Book, Boolean> statusCol;


    ObservableList<Book> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       DatabaseHandler handler = DatabaseHandler.getInstance();
        initCol();
        loadData();

    }

    private void initCol() {
        photoCol.setCellValueFactory(new PropertyValueFactory<>("bookPhoto"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
    private void loadData() {
        DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
        String qu = "SELECT *  FROM Book";
        ResultSet rs = databaseHandler.execQuery(qu);
        try {
            while (rs.next()){
                //String photo = rs.getString("bookPhoto");
                String titleX = rs.getString("title");
                String authorX = rs.getString("author");
                String publisherX = rs.getString("publisher");
                String categoryX = rs.getString("category");
                Float priceX = rs.getFloat("price");
                Boolean isAvail = rs.getBoolean("status");

                list.add(new Book(titleX,authorX,publisherX,categoryX,priceX,isAvail));
            }
        }catch (SQLException ex){
            Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE,null,ex);
        }

        booksTable.getItems().setAll(list);
    }


    class Book {
       // private Image bookPhoto;
        private SimpleStringProperty title;
        private SimpleStringProperty author;
        private SimpleStringProperty publisher;
        private SimpleStringProperty category;
        private SimpleFloatProperty price;
        private SimpleBooleanProperty status;

        Book( String title, String author, String publisher, String category, Float price, Boolean status) {
            //this.bookPhoto = new Image(String.valueOf(bookPhoto));
            this.title = new SimpleStringProperty(title);
            this.author = new SimpleStringProperty(author);
            this.publisher = new SimpleStringProperty(publisher);
            this.category = new SimpleStringProperty(category);
            this.price = new SimpleFloatProperty(price);
            this.status = new SimpleBooleanProperty(status);

        }

      /*  public Image getBookPhoto() {
            return bookPhoto;
        }*/

        public String getTitle() {
            return title.get();
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

        public Boolean getStatus() {
            return status.get();
        }
    }

}
