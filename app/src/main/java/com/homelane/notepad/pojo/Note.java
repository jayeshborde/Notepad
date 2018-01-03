package com.homelane.notepad.pojo;

/**
 * Created by Jayesh on 1/3/2018.
 */

public class Note {
    int id;
    String title;
    String text;
    String image;
    String dateTime;

    public Note(){}
    public Note(int id, String title, String text, String image, String dateTime){
        this.id = id; this.title = title; this.text = text; this.image = image;
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "{ "+id+", "+" "+title+" "+dateTime+" "+image+" }";
    }
}
