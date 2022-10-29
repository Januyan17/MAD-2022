package com.hospital.booking;

public class User {

    public String username,age,email,phonenumber;

    public User(){

    }


    public User(String username,String age,String email,String phonenumber ){
        this.username=username;
        this.age=age;
        this.email=email;
        this.phonenumber=phonenumber;

    }

    public String getUsername() {
        return username;
    }

    public String getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }
}