package com.example.spearhead;

import java.util.List;

public class Playlist {
    private String playlistName;
    private int userID;
    private List<Music> musicList;
    private int thumbnail;

    public Playlist() {
    }

    public Playlist(String playlistName, int userID, List<Music> musicList) {
        this.playlistName = playlistName;
        this.userID = userID;
        this.musicList = musicList;
    }

    public Playlist(String playlistName, List<Music> musicList, int thumbnail) {
        this.playlistName = playlistName;
        this.musicList = musicList;
        this.thumbnail = thumbnail;
    }

    public Playlist(String playlistName, int userID, List<Music> musicList, int thumbnail) {
        this.playlistName = playlistName;
        this.userID = userID;
        this.musicList = musicList;
        this.thumbnail = thumbnail;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public List<Music> getMusicList() {
        return musicList;
    }

    public void setMusicList(List<Music> musicList) {
        this.musicList = musicList;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
