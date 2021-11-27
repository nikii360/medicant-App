package com.example.medcare;

public class Doctor {
    private String ID, fullName, mailId, gender,Dob,region, Address, contactNo, Specialties, academicQualification, Experience;

    public Doctor(String ID, String fullName, String mailId, String gender, String dob, String region, String address, String contactNo, String specialties, String academicQualification, String experience) {
        this.ID=ID;
        this.fullName = fullName;
        this.mailId = mailId;
        this.gender = gender;
        this.Dob = dob;
        this.region = region;
        this.Address = address;
        this.contactNo = contactNo;
        this.Specialties = specialties;
        this.academicQualification = academicQualification;
        this.Experience = experience;
    }

    public String getID() {
        return ID;
    }

    public Doctor(String fullName, String mailId, String gender, String dob, String region, String address, String contactNo, String specialties, String academicQualification, String experience) {
        this.fullName = fullName;
        this.mailId = mailId;
        this.gender = gender;
        this.Dob = dob;
        this.region = region;
        this.Address = address;
        this.contactNo = contactNo;
        this.Specialties = specialties;
        this.academicQualification = academicQualification;
        this.Experience = experience;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return Dob;
    }

    public void setDob(String dob) {
        Dob = dob;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getSpecialties() {
        return Specialties;
    }

    public void setSpecialties(String specialties) {
        Specialties = specialties;
    }

    public String getAcademicQualification() {
        return academicQualification;
    }

    public void setAcademicQualification(String academicQualification) {
        this.academicQualification = academicQualification;
    }

    public String getExperience() {
        return Experience;
    }

    public void setExperience(String experience) {
        Experience = experience;
    }
}
