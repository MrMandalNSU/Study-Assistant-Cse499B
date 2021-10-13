package com.example.doctalk;

public class UserRegistration {

    public String fullname, email, phone, userType;


    public UserRegistration() {

    }

    public UserRegistration(String fullname,String email,String phone,String userType)
    {
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.userType = userType;
    }
}
