package library.books;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import library.database.Libdb;
import library.members.Member;

public class RentDate implements Initializable {

    @FXML
    private JFXTextField DateOfRent;
    @FXML
    private JFXButton submit;
    @FXML
    private JFXButton datebeker  ;
    Libdb L=new Libdb();

    String QU;

//============================================================================================
    public  void  addBook(Books B,Member m){


        QU="SELECT FROM BOOKS WHERE BOOKS TITLE=TITLE";
        ResultSet R= L.execQuery(QU);
        try{
            while (R.next()){
                String TITLE=R.getString("TITLE");
                System.out.println(B.getTitle());


            }

        }
        catch (SQLException ex){

            Logger.getLogger("vvvvv");

        }

    }

//=========================================================================================

    public void rent (ActionEvent event, Member m, Books B){

        if (B.getAvailability()) {
            addBook(B,m);

            // add date of day to database



            QU="Select FROM BOOKS WHERE BOOKS TITLE=title";
            ResultSet R= L.execQuery(QU);
            try{
                while (R.next()){
                    String TITLE=R.getString("TITLE");


                }

            }
            catch (SQLException ex){

                Logger.getLogger("vvvvv");

            }            //add beriod of rent to database


        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("this book is un avilable");
            alert.showAndWait();

        }


    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}



