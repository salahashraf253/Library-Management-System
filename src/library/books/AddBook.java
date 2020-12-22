package library.books;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
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
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;
    @FXML
    private StackPane rootPane;
    @FXML
    private AnchorPane mainContainer;

    public void initialize(URL url, ResourceBundle rb) {
        databaseHandler = DatabaseHandler.getInstance();
    }


    private void addBook(ActionEvent event) {
        String bookID = id.getText();
        String bookAuthor = author.getText();
        String bookName = title.getText();
        String bookPublisher = publisher.getText();
        if (bookID.isEmpty() || bookAuthor.isEmpty() || bookName.isEmpty()||bookPublisher.isEmpty()) {
            Alert alert = new Alert(Alert.Alerttype.ERROR);
            alert.setheadertext(null);
            alert.setContentText("Please enter data in all fields");
            alert.showandwait();
            return;
            String qu="INSERT INTO BOOK VALUES("+
                    "'"+bookID+"',"+
            "'"+bookName+"',"+
            "'"+bookAuthor+"',"+
            "'"+bookPublisher+"',"+
            ""+true+""+')';
            System.out.println(qu);
            if(Libdb.execAction(qu)){
                Alert alert = new Alert(Alert.Alerttype.INFORMATION);
                alert.setheadertext(null);
                alert.setContentText("SUCCESS");
                alert.showandwait();
            }
            else
                Alert alert = new Alert(Alert.Alerttype.ERROR);
            alert.setheadertext(null);
            alert.setContentText("Failed");
            alert.showandwait();

        }

    }
}
