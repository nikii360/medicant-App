package com.example.medcare;

public class Booking {
    String booking_id,user_id,doc_id, date, time,booking_status;

    public Booking(String booking_id, String user_id, String doc_id, String date, String time, String booking_status) {
        this.booking_id = booking_id;
        this.user_id = user_id;
        this.doc_id = doc_id;
        this.date = date;
        this.time = time;
        this.booking_status = booking_status;
    }

    public Booking(String booking_id, String user_id, String doc_id, String date, String time) {
        this.booking_id = booking_id;
        this.user_id = user_id;
        this.doc_id = doc_id;
        this.date = date;
        this.time = time;
        this.booking_status= "waitlist";
    }

    public Booking() {
        this.booking_status="waitlist";
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBooking_status() {
        return booking_status;
    }

    public void setBooking_status(String booking_status) {
        this.booking_status = booking_status;
    }
}
