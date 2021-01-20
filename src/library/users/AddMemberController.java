package library.users;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import library.database.DatabaseHandler;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


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
    private ImageView memberPhoto;
    @FXML
    private JFXButton uploadPhotoBtn;
    @FXML
    private StackPane rootPane;
    @FXML
    private AnchorPane addMemberPane;

    DatabaseHandler handler;
    public void initialize(URL url, ResourceBundle rb) throws IOException {
        handler = DatabaseHandler.getInstance();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("AddMember.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void uploadPhoto(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Image files (*.png,*.jpg)", "*.png","*.jpg");
        fileChooser.getExtensionFilters().add(extFilter);
        File selectedFile = fileChooser.showOpenDialog(null);

        if(selectedFile != null){
            Image image = new Image(selectedFile.toURI().toString());
            String imageUrl = selectedFile.getPath();
            memberPhoto.setImage(image);
        }else {
            JOptionPane.showMessageDialog(null,"File Not Valid","Invalid Image",JOptionPane.WARNING_MESSAGE);
        }
    }
//store date

    @FXML
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
        handler.execAction(qu);
        if(handler.execAction(qu)){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("id");
            alert.showAndWait();
            Stage stage = (Stage) addMemberPane.getScene().getWindow();
            stage.close();
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
        Stage stage = (Stage) addMemberPane.getScene().getWindow();
        stage.close();
    }
}