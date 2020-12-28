package library.users;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import library.database.Libdb;



public class AddAdminController implements Initializable {

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
    private JFXButton addAdminBtn;
    @FXML
    private JFXButton cancelBtn;
    @FXML
    private JFXButton uploadPhotoBtn;
    @FXML
    private ImageView photo;

    Libdb Libdb;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setUploadPhotoBtn(JFXButton uploadPhotoBtn) {
        this.uploadPhotoBtn = uploadPhotoBtn;
    }

}
