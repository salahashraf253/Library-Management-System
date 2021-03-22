package library.books;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import library.database.DatabaseConnection;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

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
    File selectedFile;

    DatabaseConnection connectNow = new DatabaseConnection();
    Connection connectDB = connectNow.getConnection();

    public void initialize(URL url, ResourceBundle rb) {
        //ComboBox Items ========================================
        category.getItems().addAll("Arts & Music","Business","Cooking","Comics","Computer & Tech",
                "Education","Health & Fitness","History","Home & Garden","Kids","Literature","Mathematics",
                "Medical","Novels","Programming","Science","Sci-Fiction","Sports","Travel");
        //=======================================================

    }

    //Database =====================
    @FXML
    private void uploadPhoto(){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter;
        extFilter = new FileChooser.ExtensionFilter("Image files (*.png,*.jpg)", "*.png","*.jpg");
        fileChooser.getExtensionFilters().add(extFilter);
        selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null){
            Image image = new Image(selectedFile.toURI().toString());
            bookCover.setImage(image);
        }else {
            JOptionPane.showMessageDialog(null,"File Not Valid","Invalid Image",JOptionPane.WARNING_MESSAGE);
        }
    }

    @FXML
    private void addBook() throws IOException {
        String bookID = isbn.getText();
        String bookAuthor = author.getText();
        String bookTitle = title.getText();
        String bookPublisher = publisher.getText();
        String bookCategory = category.getValue();
        String bookPrice = price.getText();

        if (bookID.isEmpty() || bookAuthor.isEmpty() || bookTitle.isEmpty()||bookPublisher.isEmpty() || bookCategory.isEmpty() || bookPrice.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter data in all fields");
            alert.showAndWait();
            return;}
            String qu ="INSERT INTO libraryassistant.books(cover,book_title,book_id,author,publisher,category,price,is_available) VALUES(?,"+
                    "'"+bookTitle+"',"+
                    "'"+bookID+"',"+
                    "'"+bookAuthor+"',"+
                    "'"+bookPublisher+"',"+
                    "'"+bookCategory+"',"+
                    "'"+bookPrice+"',"+
                    ""+true+""+")";
        try {
            FileInputStream fileInputStream =new FileInputStream(selectedFile);
            PreparedStatement pst = connectDB.prepareStatement(qu);
            pst.setBlob(1,fileInputStream,fileInputStream.available());
            pst.executeUpdate();
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        cancel();
    }
    @FXML
    private void cancel (){
        Stage stage = (Stage) addBookPane.getScene().getWindow();
        stage.close();
    }
    /*private void checkdata()
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
*/
}





