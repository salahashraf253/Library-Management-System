package library.main;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import library.books.Books;
import library.users.Member;

import javax.swing.*;
import java.io.IOException;

public class DashboardController {
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab homeTab;
    @FXML
    private TextField searchBookTxt;
    @FXML
    private JFXButton searchBookBtn;
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
    private JFXButton buyBtn;
    @FXML
    private JFXButton rentBtn;

    Books book = new Books();
    Member member = new Member();


    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void rent (ActionEvent event) throws IOException {
        if(member.getMemberStatus()) {
            book.rentBook(member,book);
        } else {
            JOptionPane.showMessageDialog(null,"You're Blocked. You can't rent any Book");
        }
    }


    public JFXButton getRentBtn() {
        return rentBtn;
    }

    public void setRentBtn(JFXButton rentBtn) {
        this.rentBtn = rentBtn;
    }
}
