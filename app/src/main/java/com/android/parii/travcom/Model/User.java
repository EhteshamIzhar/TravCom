package com.android.parii.travcom.Model;


public class User {
    private String Name;
    private String Password;

    public User(){

    }
    //Constructor
    public User(String name, String password) {
        Name = name;
        Password = password;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public String getPassword() {
        return Password;
    }
}
