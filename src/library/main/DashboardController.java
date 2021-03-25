package library.main;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import library.books.ViewBooksController;
import library.users.Member;
import library.users.ViewMembersController;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    protected ViewBooksController viewBooksController;
    @FXML
    protected ViewMembersController viewMembersController;
    @FXML
    private BorderPane border_pane;
    @FXML
    private AnchorPane titlePane;
    @FXML
    private AnchorPane home_root_pane;
    @FXML
    private AnchorPane home_pane;
    @FXML
    private JFXButton logoutBtn;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab homeTab;
    @FXML
    private AnchorPane viewBooks;
    @FXML
    private AnchorPane viewMembers;
    @FXML
    private TextField searchBookTxt;
    @FXML
    private JFXButton searchBookBtn;
    @FXML
    private TextField searchMemberTxt;
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
    private JFXButton buyBtn;
    @FXML
    private JFXButton rentBtn;
    @FXML
    private JFXButton viewBooksBtn;
    @FXML
    private JFXButton view_members_btn;

    Member member = new Member();

    public JFXButton getRentBtn() {
        return rentBtn;
    }

    @FXML
    void logout(){
        closeWindow(border_pane);
        loadWindowDecorated("/library/main/Login.fxml", "Login",false);
    }
    @FXML
    void rent() throws IOException {
        if(member.getMemberStatus()) {

        } else {
            JOptionPane.showMessageDialog(null,"You're Blocked. You can't rent any Book");
        }
    }
    @FXML
    public void searchBook(){
        String title = searchBookTxt.getText();
        String btnId = searchBookBtn.getId();
        viewBooksController.loadData(btnId,title);
        viewBooks.setVisible(true);
    }
    @FXML
    public void searchMember(){
        String name = searchMemberTxt.getText();
        String btnId = searchMemberBtn.getId();
        viewMembersController.loadData(btnId,name);
        viewMembers.setVisible(true);
    }
    @FXML
    private void viewMembers() {
        String btnId = view_members_btn.getId();
        viewMembersController.loadData(btnId,null);
        viewMembers.setVisible(true);
    }
    @FXML
    private void viewBooks() {
        String btnId = viewBooksBtn.getId();
        viewBooksController.loadData(btnId,null);
        viewBooks.setVisible(true);
    }
    @FXML
    private void settings() {
        loadWindow("/library/main/Settings.fxml","Settings",false);
    }

    void loadWindow(String loc, String title,Boolean max){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.setMaximized(max);
            stage.show();
        } catch (IOException e) {
            //Logger.getLogger(AddAdminController.class.getName().log(Level.SEVERE, null, ex));
            e.printStackTrace();
        }
    }
    void loadWindowDecorated(String loc, String title,Boolean max){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.setMaximized(max);
            stage.show();
        } catch (IOException e) {
            //Logger.getLogger(AddAdminController.class.getName().log(Level.SEVERE, null, ex));
            e.printStackTrace();
        }
    }
    void closeWindow(BorderPane pane){
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewBooks.setVisible(false);
        viewMembers.setVisible(false);
    }
}
