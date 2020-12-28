package library.users;

import library.books.Books;

import java.lang.reflect.Member;

abstract public class User {
    String Id ;
    int phone;
    String name;
    String address;
    int password;
    String email;
    String type;

    abstract void search(Member m);
    abstract  void rent(Member m, Books B,String beriod);


    abstract void rent(library.users.Member m, Books b, String beriod);
}