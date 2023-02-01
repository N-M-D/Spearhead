package com.example.spearhead;

public class Time {
    private int largeNum;
    private int bigNum;
    private int medNum;
    private int smallNum;
    private int tinyNum;

    public Time(int largeNum, int bigNum, int medNum, int smallNum, int tinyNum) {
        this.largeNum = largeNum;
        this.bigNum = bigNum;
        this.medNum = medNum;
        this.smallNum = smallNum;
        this.tinyNum = tinyNum;
    }

    public int getLargeNum() {
        return largeNum;
    }

    public void setLargeNum(int largeNum) {
        this.largeNum = largeNum;
    }

    public int getBigNum() {
        return bigNum;
    }

    public void setBigNum(int bigNum) {
        this.bigNum = bigNum;
    }

    public int getMedNum() {
        return medNum;
    }

    public void setMedNum(int medNum) {
        this.medNum = medNum;
    }

    public int getSmallNum() {
        return smallNum;
    }

    public void setSmallNum(int smallNum) {
        this.smallNum = smallNum;
    }

    public int getTinyNum() {
        return tinyNum;
    }

    public void setTinyNum(int tinyNum) {
        this.tinyNum = tinyNum;
    }
}
