package com.example.doctalk;

import java.io.Serializable;

public class PatientHelperClass {


    String name, age, location, phone, addiSymptoms,gender,doctorName;
    int position;

    public PatientHelperClass() {

    }

    public PatientHelperClass(String name, String age, String location, String phone, String addiSymptoms,String gender,String doctorName) {
        this.name = name;
        this.age = age;
        this.location = location;
        this.phone = phone;
        this.addiSymptoms = addiSymptoms;
        this.gender = gender;
        this.doctorName= doctorName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddiSymptoms() {
        return addiSymptoms;
    }

    public void setAddiSymptoms(String addiSymptoms) {
        this.addiSymptoms = addiSymptoms;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

}
