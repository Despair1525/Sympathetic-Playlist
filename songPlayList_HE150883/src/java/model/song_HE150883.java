/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


import java.util.*;
import java.sql.Date;
import java.sql.Timestamp;
/**
 *
 * @author smileymask
 */
public class song_HE150883 {
    
    private String songId;
    private String songName;
    private String artist;
    private int duration;
    private String linkSong;
    private String linkImage;
    private String userId;
    private ArrayList<String> genre;
    private int Score;
    private Timestamp songDate;
    public song_HE150883() {
    }

    public song_HE150883(String songId, String songName, String artist, int duration,  String linkSong, String linkImage,  String userId,int Score,ArrayList<String> genre) {
        this.songId = songId;
        this.songName = songName;
        this.artist = artist;
        this.duration = duration;
        this.linkSong = linkSong;
        this.linkImage = linkImage;
        this.userId = userId;
        this.Score = Score;
        this.genre = genre;
    }
    
    public song_HE150883(String songId, String songName, String artist, int duration,  String linkSong, String linkImage,  String userId,int Score,ArrayList<String> genre,Timestamp songDate) {
        this.songId = songId;
        this.songName = songName;
        this.artist = artist;
        this.duration = duration;
        this.linkSong = linkSong;
        this.linkImage = linkImage;
        this.userId = userId;
        this.Score = Score;
        this.genre = genre;
        this.songDate = songDate;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

  

   

    public String getLinkSong() {
        return linkSong;
    }

    public void setLinkSong(String linkSong) {
        this.linkSong = linkSong;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }

   

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList<String> getGenre() {
        return genre;
    }

    public void setGenre(ArrayList<String> genre) {
        this.genre = genre;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int Score) {
        this.Score = Score;
    }

    public Timestamp getSongDate() {
        return songDate;
    }

    public void setSongDate(Timestamp songDate) {
        this.songDate = songDate;
    }
    
    
    
}
