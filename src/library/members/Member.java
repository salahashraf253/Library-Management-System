package library.members;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.Alert;
import library.books.AddBook;
import library.database.Libdb;
import library.books.Books;



public class Member extends User {
    Libdb libdb=new Libdb();
    public Member(String Id, int phone, String name, String address, int password, String email, String type) {
        this.Id = Id;
        this.email = email;
        this.address = address;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.type = type;
    }
    void add_book(Books B ,Member m){
        // add book from data base;

    }


    void search(Member m) {
        String qu = "SELECT * FROM MEMBERS WHERE memberid=id";
        ResultSet rs = libdb.execQuery(qu);
        try {
            while (rs.next()) {
                String titlex = rs.getString("id");
                System.out.println(titlex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddBook.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    void search(java.lang.reflect.Member m) {

    }

    @Override
    void rent(java.lang.reflect.Member m, Books B, String beriod) {

    }

    @Override
    void rent(Member m, Books b , String beriod) {
        if(b.getAvailability()) {
            m.add_book(b, m);
            int date;
            // add date of day to database
            //add beriod of rent to database
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("this book is un avilable");
            alert.showAndWait();

        }


    }}

