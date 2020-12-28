package library.main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import library.books.AddBookController;
import library.books.ViewBooksController;
import library.users.AddAdminController;
import library.users.AddMemberController;
import library.users.ViewMembersController;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminDashboardController implements Initializable {

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
    private JFXTextField searchBookTxt;
    @FXML
    private JFXTextField searchMemberTxt;
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

    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("AdminDashboard.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void addAdmin(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddAdmin.fxml"));
            Parent root = (Parent) loader.load();

            AddAdminController addAdminController = loader.getController();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void addMember(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddMember.fxml"));
            Parent root = (Parent) loader.load();

            AddMemberController addMemberController = loader.getController();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    void addBook(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddBook.fxml"));
            Parent root = (Parent) loader.load();

            AddBookController addBookController = loader.getController();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void viewMembers(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewMembers.fxml"));
            Parent root = (Parent) loader.load();

            ViewMembersController viewMembersController = loader.getController();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void viewBooks(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewBooks.fxml"));
            Parent root = (Parent) loader.load();

            ViewBooksController viewBooksController = loader.getController();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void settings(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Settings.fxml"));
            Parent root = (Parent) loader.load();

            SettingsController settingsController = loader.getController();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
