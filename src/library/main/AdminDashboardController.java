package library.main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminDashboardController implements Initializable{

    @FXML
    private Tab adminHomeTab;
    @FXML
    private Tab adminCategoriesTab;
    @FXML
    private AnchorPane adminAccountTab;
    @FXML
    private JFXTextField adminAddress;
    @FXML
    private JFXTextField adminMobile;
    @FXML
    private JFXTextField adminName;
    @FXML
    private JFXTextField adminEmail;
    @FXML
    private JFXPasswordField adminPassword;
    @FXML
    private JFXButton updateAdminBtn;
    @FXML
    private JFXButton addBookBtn;
    @FXML
    private JFXButton addAdminBtn;
    @FXML
    private JFXButton addMemberBtn;
    @FXML
    private JFXButton viewBooksBtn;
    @FXML
    private JFXButton viewMembersBtn;
    @FXML
    private JFXButton settingsBtn;
    @FXML
    private TextField searchBookTxt;
    @FXML
    private TextField searchMemberTxt;
    @FXML
    private JFXButton searchBookBtn;
    @FXML
    private JFXButton searchMemberBtn;
    @FXML
    private Label title;
    @FXML
    private Label author;
    @FXML
    private Label publisher;
    @FXML
    private Label category;
    @FXML
    private Label price;
    @FXML
    private JFXButton editBookBtn;
    @FXML
    private JFXButton removeBooKBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void addAdmin(){
        loadWindow("/library/users/AddAdmin.fxml", "Add New Admin");
    }
    @FXML
    private void addMember(ActionEvent event){
      loadWindow("/library/users/AddMember.fxml", "Add New Member");
    }
    @FXML
    private void addBook(ActionEvent event) {
    loadWindow("/library/books/AddBook.fxml" , "Add New Book");
    }
    @FXML
    private void viewMembers(ActionEvent event) {
        loadWindowDecorated("/library/users/ViewMembers.fxml", "All Members");
    }
    @FXML
    private void viewBooks(ActionEvent event) {
        loadWindowDecorated("/library/books/ViewBooks.fxml" , "All Books");
    }
    @FXML
    private void settings(ActionEvent event) {
        loadWindow("/library/main/Settings.fxml", "Settings");
    }

    void loadWindow(String loc, String title){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.TRANSPARENT);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            //Logger.getLogger(AddAdminController.class.getName().log(Level.SEVERE, null, ex));
            e.printStackTrace();
        }
    }
    void loadWindowDecorated(String loc, String title){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            //Logger.getLogger(AddAdminController.class.getName().log(Level.SEVERE, null, ex));
            e.printStackTrace();
        }
    }


}
