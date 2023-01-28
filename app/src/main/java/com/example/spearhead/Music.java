package com.example.spearhead;

public class Music {
    private int ID;
    private String name;
    private String artist;
    private int img;

    public Music() {

    }

    public Music(int img, String name, String artist) {
        this.name = name;
        this.artist = artist;
        this.img = img;
    }

    public Music(String name, String artist, int img, int ID) {
        this.ID = ID;
        this.name = name;
        this.artist = artist;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int filename) {
        this.img = filename;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}