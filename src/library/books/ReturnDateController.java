package library.books;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import library.users.Member;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ReturnDateController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private JFXDatePicker returnDate;
    @FXML
    private JFXButton submitBtn;
    @FXML
    private JFXButton cancelBtn;

    Books book = new Books();
    Member m = new Member();

    String QU;
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("RentDate.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void submit(ActionEvent event){
        LocalDate currentDate = LocalDate.now();
        LocalDate bookReturnDate = returnDate.getValue();
        if(bookReturnDate!=null) {
            book.addRentedBook(book, m, currentDate, bookReturnDate);
            book.showRentedBooks(m,book);
            book.setBookAvailability(false);
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Return Date cannot be Empty");
            alert.showAndWait();
        }
    }
   @FXML
   void cancel(ActionEvent event) {
       Stage stage = (Stage) rootPane.getScene().getWindow();
       stage.close();
   }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}



