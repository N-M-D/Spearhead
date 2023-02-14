package com.example.spearhead;

public class HomeRecyclerItem {

    private int homeCardImage;
    private String homeCardTitle;
    private String homeCardDate;

    public HomeRecyclerItem(int homeCardImage, String homeCardTitle, String homeCardDate)
    {
        this.homeCardImage = homeCardImage;
        this.homeCardTitle = homeCardTitle;
        this.homeCardDate = homeCardDate;
    }

    public int getHomeCardImage() { return homeCardImage; }

    public String getHomeCardTitle() { return homeCardTitle; }

    public String getHomeCardDate()
    {
        return homeCardDate;
    }

}
