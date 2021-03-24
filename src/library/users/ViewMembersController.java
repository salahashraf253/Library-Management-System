package library.users;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import library.database.DatabaseConnection;

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ViewMembersController implements Initializable {
    @FXML
    private AnchorPane viewMembersPane;
    @FXML
    private TableView membersTable;
    @FXML
    private TableColumn<Member, String> idCol;
    @FXML
    private TableColumn<Member, String> firstNameCol;
    @FXML
    private TableColumn<Member, String> lastNameCol;
    @FXML
    private TableColumn<Member, String> addressCol;
    @FXML
    private TableColumn<Member, String> mobileCol;
    @FXML
    private TableColumn<Member, String> emailCol;

    ObservableList<Member> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initCol();
    }

    private void initCol() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        mobileCol.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
    }
    public void loadData(String btnId, String searchTxt) {
        list.clear();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String viewMembers = "SELECT *  FROM user_account";
        String searchMember = "SELECT * FROM user_account WHERE user_type='user' AND (first_name LIKE ? OR last_name LIKE ?)";
        Statement stmt = null;
        try {
            PreparedStatement pst = connectDB.prepareStatement(searchMember);
            pst.setString(1,"%"+searchTxt+"%");
            pst.setString(2,"%"+searchTxt+"%");
            stmt = connectDB.createStatement();
            ResultSet rs=null;
            if(btnId.equals("view_members_btn")){
                rs = stmt.executeQuery(viewMembers);
            }else if(btnId.equals("searchMemberBtn")){
                rs= pst.executeQuery();
            }else {
                JOptionPane.showMessageDialog(null,"No Member Found");
            }
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
                    list.add(new Member(id,firstName,lastName,address,mobile,email));
                }
            }catch (SQLException ex){
                ex.printStackTrace();
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        membersTable.getItems().setAll(list);
    }
    public void back() {
        viewMembersPane.setVisible(false);
    }

    public void refresh() {
        loadData("view_members_btn",null);
    }
/*
    public static class Members extends Member {
        private SimpleIntegerProperty id;
        private SimpleStringProperty firstName;
        private SimpleStringProperty lastName;
        private SimpleIntegerProperty phone;
        private SimpleStringProperty address;
        private SimpleStringProperty email;


        Members(int id, String firstName, String lastName, String address, int phone, String email) {
            this.id = new SimpleIntegerProperty(id);
            this.firstName = new SimpleStringProperty(firstName);
            this.lastName = new SimpleStringProperty(lastName);
            this.address = new SimpleStringProperty(address);
            this.email = new SimpleStringProperty(email);
            this.phone = new SimpleIntegerProperty(phone);

        }
    }*/
}
