package com.example.ricoj.poopapp;

import android.widget.EditText;

/**
 * Created by ricoj on 1/30/2017.
 */

public class Poop {


    private String type;
    private String color;
    private String struggle;
    private String smell;
    private String numOfWipes;
    private String duration;
    private String notes;



    private String dateAndTime;

    public Poop() {

    }

    public Poop( String type, String color, String struggle, String smell, String numOfWipes, String duration, String notes, String dateAndTime) {

        this.type = type;
        this.color = color;
        this.struggle = struggle;
        this.smell = smell;
        this.numOfWipes = numOfWipes;
        this.duration = duration;
        this.notes = notes;
        this.dateAndTime = dateAndTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getStruggle() {
        return struggle;
    }

    public void setStruggle(String struggle) {
        this.struggle = struggle;
    }

    public String getSmell() {
        return smell;
    }

    public void setSmell(String smell) {
        this.smell = smell;
    }

    public String getNumOfWipes() {
        return numOfWipes;
    }

    public void setNumOfWipes(String numOfWipes) {
        this.numOfWipes = numOfWipes;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }


    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }
}
