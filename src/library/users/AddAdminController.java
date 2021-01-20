package library.users;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import library.database.DatabaseHandler;

import java.net.URL;
import java.util.ResourceBundle;



public class AddAdminController implements Initializable {

    @FXML
    private AnchorPane addAdminForm;
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

    DatabaseHandler DatabaseHandler;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setUploadPhotoBtn(JFXButton uploadPhotoBtn) {
        this.uploadPhotoBtn = uploadPhotoBtn;
    }
    @FXML
    private void cancel (ActionEvent event){
        Stage stage = (Stage) addAdminForm.getScene().getWindow();
        stage.close();
    }
}
