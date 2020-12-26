package library.members;
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


public class AddMember extends Application {
    @FXML
    private JFXTextField fullname;
    @FXML
    private JFXTextField adress;
    @FXML
    private JFXTextField mobile;
    @FXML
    private JFXTextField email;
    @FXML
    private  JFXTextField password;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;
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

    private void addmember(ActionEvent event) {
        String memberfullname = fullname.getText();
        String memberadress = adress.getText();
        String membermobile = mobile.getText();
        String  memberemail = email.getText();
        String memberpassword = password.getText();
        if (memberfullname.isEmpty() || memberadress.isEmpty() || membermobile.isEmpty()||memberemail.isEmpty() || memberpassword.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter data in all fields");
            alert.showAndWait();
            return;}
        String qu="INSERT INTO MEMBER VALUES("+
                "'"+memberfullname+"',"+
                "'"+memberadress+"',"+
                "'"+membermobile+"',"+
                "'"+memberemail+"',"+
                "'"+memberpassword+"',"+
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