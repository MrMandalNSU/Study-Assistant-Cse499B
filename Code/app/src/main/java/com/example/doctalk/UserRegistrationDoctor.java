package com.example.doctalk;

public class UserRegistrationDoctor {

    public String fullname, email, phone, userType,licenseNumber;
    public int position;


    public UserRegistrationDoctor() {

    }

    public UserRegistrationDoctor(String fullname,String email,String phone,String userType,String licenseNumber)
    {
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.userType = userType;
        this.licenseNumber = licenseNumber;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

}
