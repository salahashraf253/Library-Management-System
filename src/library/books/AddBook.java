package library.books;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import library.database.Libdb;
import javafx.stage.Stage;


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


    private void addBook(ActionEvent event) {
        String bookID = id.getText();
        String bookAuthor = author.getText();
        String bookName = title.getText();
        String bookPublisher = publisher.getText();
        if (bookID.isEmpty() || bookAuthor.isEmpty() || bookName.isEmpty()||bookPublisher.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please enter data in all fields");
            alert.showAndWait();
            return;
            String qu = "INSERT INTO BOOK VALUES(" +
                    "'" + bookID + "'," +
                    "'" + bookName + "'," +
                    "'" + bookAuthor + "'," +
                    "'" + bookPublisher + "'," +
                    "" + true + "" + ')';
            System.out.println(qu);
            if(Libdb.execAction(qu)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("SUCCESS");
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
}





