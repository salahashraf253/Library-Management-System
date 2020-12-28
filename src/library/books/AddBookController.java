package library.books;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
//import library.assistant.alert.AlertMaker;
//port library.assistant.data.model.Book;
//import library.assistant.database.DataHelper;
import library.database.Libdb;
public class AddBookController implements Initializable {
    @FXML
    private JFXTextField title;
    @FXML
    private JFXTextField isbn;
    @FXML
    private JFXTextField author;
    @FXML
    private JFXTextField publisher;
    @FXML
    private JFXComboBox<String> category;
    @FXML
    private JFXTextField price;
    @FXML
    private JFXButton addBookBtn;
    @FXML
    private JFXButton cancelBtn;
    @FXML
    private StackPane rootPane;
    @FXML
    private AnchorPane mainContainer;
    Libdb Libdb;
    public void initialize(URL url, ResourceBundle rb) {
        Libdb =new Libdb();
        //ComboBox Items ========================================
        category.getItems().add("Arts & Music");
        category.getItems().add("Business");
        category.getItems().add("Cooking");
        category.getItems().add("Comics");
        category.getItems().add("Computer & Tech");
        category.getItems().add("Education");
        category.getItems().add("Health & Fitness");
        category.getItems().add("History");
        category.getItems().add("Home & Garden");
        category.getItems().add("Kids");
        category.getItems().add("Literature");
        category.getItems().add("Mathematics");
        category.getItems().add("Medical");
        category.getItems().add("Novels");
        category.getItems().add("Programming");
        category.getItems().add("Science");
        category.getItems().add("Sci-Fiction");
        category.getItems().add("Sports");
        category.getItems().add("Travel");
        //=======================================================

    }

    //Database =====================
    private void addBook(ActionEvent event) {
        String bookID = isbn.getText();
        String bookAuthor = author.getText();
        String bookTitle = title.getText();
        String bookPublisher = publisher.getText();
        //String bookCategory = category.getText;

        if (bookID.isEmpty() || bookAuthor.isEmpty() || bookTitle.isEmpty()||bookPublisher.isEmpty() /* || bookCategory.isEmpty()*/) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter data in all fields");
            alert.showAndWait();
            return;}
        String qu="INSERT INTO BOOK VALUES("+
                "'"+bookID+"',"+
                "'"+bookTitle+"',"+
                "'"+bookAuthor+"',"+
                "'"+bookPublisher+"',"+
                "'"+//bookCategory+"',"+
                ""+true+""+")";
        System.out.println(qu);

        if(Libdb.execAction(qu)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("id");
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Failed");
            alert.showAndWait();

        }

    }
    @FXML
    private void cancel (ActionEvent event){
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
    private void checkdata()
    {
        String qu = "SELECT title FROM BOOK";
        ResultSet rs = Libdb.execQuery(qu);
        try {
            while (rs.next()) {
                String titlex = rs.getString("title");
                System.out.println(titlex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}





