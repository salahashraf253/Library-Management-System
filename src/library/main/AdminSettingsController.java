package library.main;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import library.database.DatabaseConnection;
import library.users.Admin;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AdminSettingsController implements Initializable {
    @FXML
    private AnchorPane settingsPane;
    @FXML
    private TableView adminsTable;
    @FXML
    private JFXButton close_btn;
    @FXML
    private TableColumn<Admin, String> idCol;
    @FXML
    private TableColumn<Admin, String> firstNameCol;
    @FXML
    private TableColumn<Admin, String> lastNameCol;
    @FXML
    private TableColumn<Admin, String> addressCol;
    @FXML
    private TableColumn<Admin, String> mobileCol;
    @FXML
    private TableColumn<Admin, String> emailCol;
    @FXML
    private TableColumn<Admin, JFXButton> removeCol;
    @FXML
    private TableColumn<Admin, JFXButton> blockCol;

    ObservableList<Admin> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initCol();
        loadData();
    }

    private void initCol() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        mobileCol.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        removeCol.setCellValueFactory(new PropertyValueFactory<>("removeAdminBtn"));
        blockCol.setCellValueFactory(new PropertyValueFactory<>("blockAdminBtn"));
    }
    public void loadData() {
        String adminStatus = null;
        list.clear();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String viewAdmins = "SELECT *  FROM user_account WHERE user_type='admin'";

        try {
            Statement stmt = connectDB.createStatement();
            ResultSet rs=stmt.executeQuery(viewAdmins);
            try {
                while (true){
                    assert rs != null;
                    if (!rs.next()) break;
                    int id =rs.getInt("user_id");
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String address = rs.getString("address");
                    int mobile = rs.getInt("mobile");
                    String email = rs.getString("email");
                    Boolean isBlocked = rs.getBoolean("is_blocked");
                    if (isBlocked)
                        adminStatus = "Blocked";
                    else
                        adminStatus= "Not Blocked";
                    list.add(new Admin(id,firstName,lastName,address,mobile,email,null,adminStatus));
                }
            }catch (SQLException ex){
                ex.printStackTrace();
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        adminsTable.getItems().setAll(list);
    }
    public void refresh() {
        loadData();
    }
    @FXML
    protected void close(){
        Stage stage = (Stage) settingsPane.getScene().getWindow();
        stage.close();
    }
}

