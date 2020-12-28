package library.users;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import library.database.Libdb;


public class AddMemberController extends Application {
    @FXML
    private JFXTextField fullName;
    @FXML
    private JFXTextField address;
    @FXML
    private JFXTextField mobile;
    @FXML
    private JFXTextField email;
    @FXML
    private  JFXTextField password;
    @FXML
    private JFXButton addMemberBtn;
    @FXML
    private JFXButton cancelBtn;
    @FXML
    private JFXButton uploadPhotoBtn;
    @FXML
    private StackPane rootPane;
    @FXML
    private AnchorPane mainContainer;
    Libdb Libdb;
    public void initialize(URL url, ResourceBundle rb) throws IOException {
        Libdb =new Libdb();


    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("AddMember.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
//store date

    private void addMember(ActionEvent event) {
        String memberFullName = fullName.getText();
        String memberAddress = address.getText();
        String memberMobile = mobile.getText();
        String  memberEmail = email.getText();
        String memberPassword = password.getText();
        if (memberFullName.isEmpty() || memberAddress.isEmpty() || memberMobile.isEmpty()||memberEmail.isEmpty() || memberPassword.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter data in all fields");
            alert.showAndWait();
            return;}
        String qu="INSERT INTO MEMBER VALUES("+
                "'"+memberFullName+"',"+
                "'"+memberAddress+"',"+
                "'"+memberMobile+"',"+
                "'"+memberEmail+"',"+
                "'"+memberPassword+"',"+
                ""+true+""+")";
        System.out.println(qu);

        if(Libdb.execAction(qu)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("id");
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Failed");
            alert.showAndWait();

        }

    }


}