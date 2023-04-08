package com.example.myapplication2;

import java.io.Serializable;

public class Expensess implements Serializable {
    private int id;
    private String type ;
    private String date;
    private String time ;
    private String amount;
    private String comment ;
    private int trip_id;

    public Expensess(int id, String type, String date, String time, String amount, String comment, int trip_id) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.time = time;
        this.amount = amount;
        this.comment = comment;
        this.trip_id = trip_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(int trip_id) {
        this.trip_id = trip_id;
    }
}

