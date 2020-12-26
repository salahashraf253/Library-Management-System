package library.books;
import com.jfoenix.controls.JFXButton;
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
public class  AddBook implements Initializable {
    @FXML
    private JFXTextField title;
    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField author;
    @FXML
    private JFXTextField publisher;
    @FXML
    private  JFXTextField category;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;
    @FXML
    private StackPane rootPane;
    @FXML
    private AnchorPane mainContainer;
    Libdb Libdb;
    public void initialize(URL url, ResourceBundle rb) {
        Libdb =new Libdb();

    }

    //datbase
    private void addBook(ActionEvent event) {
        String bookID = id.getText();
        String bookAuthor = author.getText();
        String bookName = title.getText();
        String bookPublisher = publisher.getText();
        String bookcategory=category.getText();
        if (bookID.isEmpty() || bookAuthor.isEmpty() || bookName.isEmpty()||bookPublisher.isEmpty() || bookcategory.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter data in all fields");
            alert.showAndWait();
            return;}
        String qu="INSERT INTO BOOK VALUES("+
                "'"+bookID+"',"+
                "'"+bookName+"',"+
                "'"+bookAuthor+"',"+
                "'"+bookPublisher+"',"+
                "'"+bookcategory+"',"+
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
            Logger.getLogger(AddBook.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}





