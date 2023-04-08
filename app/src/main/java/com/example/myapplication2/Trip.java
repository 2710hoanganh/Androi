package com.example.myapplication2;

import java.io.Serializable;

public class Trip implements Serializable {
    private int id;
    private String name ;
    private String destination;
    private String date ;
    private String desc ;
    private String accommodation;
    private String vehicle;
    private int rra;

    public Trip(int id, String name, String destination, String date, String desc, String accommodation, String vehicle, int rra) {
        this.id = id;
        this.name = name;
        this.destination = destination;
        this.date = date;
        this.desc = desc;
        this.accommodation = accommodation;
        this.vehicle = vehicle;
        this.rra = rra;
    }
    public Trip() {
        id = -1;
        name = null;
        destination = null;
        date = null;
        desc = null;
        accommodation = null;
        vehicle = null;
    }


    public int getRra() {
        return rra;
    }

    public void setRra(int rra) {
        this.rra = rra;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(String accommodation) {
        this.accommodation = accommodation;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }


    public boolean isEmpty() {
        if (-1 == id && null == name && null == destination &&null == date && null == accommodation &&null == vehicle  && -1 == rra)
            return true;

        return false;
    }
}
