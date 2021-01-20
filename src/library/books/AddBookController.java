package library.books;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import library.database.DatabaseHandler;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

//import library.assistant.alert.AlertMaker;
//port library.assistant.data.model.Book;
//import library.assistant.database.DataHelper;
public class AddBookController implements Initializable {


    @FXML
    private AnchorPane addBookPane;
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
    private JFXButton uploadPhotoBtn;
    @FXML
    private ImageView bookCover;
    @FXML
    private JFXButton addBookBtn;
    @FXML
    private JFXButton cancelBtn;

    DatabaseHandler handler;
    public void initialize(URL url, ResourceBundle rb) {
        handler = DatabaseHandler.getInstance();
        //ComboBox Items ========================================
        category.getItems().addAll("Arts & Music","Business","Cooking","Comics","Computer & Tech",
                "Education","Health & Fitness","History","Home & Garden","Kids","Literature","Mathematics",
                "Medical","Novels","Programming","Science","Sci-Fiction","Sports","Travel");
        //=======================================================

    }

    //Database =====================
    @FXML
    private void uploadPhoto(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Image files (*.png,*.jpg)", "*.png","*.jpg");
        fileChooser.getExtensionFilters().add(extFilter);
        File selectedFile = fileChooser.showOpenDialog(null);

        if(selectedFile != null){
            Image image = new Image(selectedFile.toURI().toString());
            bookCover.setImage(image);
        }else {
            JOptionPane.showMessageDialog(null,"File Not Valid","Invalid Image",JOptionPane.WARNING_MESSAGE);
        }
    }

    private void addBook(ActionEvent event) {
        String bookID = isbn.getText();
        String bookAuthor = author.getText();
        String bookTitle = title.getText();
        String bookPublisher = publisher.getText();
        String bookCategory = category.getValue();

        if (bookID.isEmpty() || bookAuthor.isEmpty() || bookTitle.isEmpty()||bookPublisher.isEmpty() || bookCategory.isEmpty()) {
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
                "'"+bookCategory+"',"+
                ""+true+""+")";
        System.out.println(qu);
        handler.execAction(qu);

        if(handler.execAction(qu)){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Member Added Successfully");
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
        Stage stage = (Stage) addBookPane.getScene().getWindow();
        stage.close();
    }
    private void checkdata()
    {
        String qu = "SELECT title FROM BOOK";
        ResultSet rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                String titleX = rs.getString("title");
                System.out.println(titleX);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}





