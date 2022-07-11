/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.util.*;
/**
 *
 * @author smileymask
 */
public class playList_HE150883 {
    private String playlistId ;
    private String playlistName;
    private boolean playlistPublic;
    private ArrayList<song_HE150883> songs;
    private String userId;

    public playList_HE150883() {
    }

    public playList_HE150883(String playlistId, String playlistName, boolean playlistPublic, ArrayList<song_HE150883> songs,String userId) {
        this.playlistId = playlistId;
        this.playlistName = playlistName;
        this.playlistPublic = playlistPublic;
        this.songs = songs;
        this.userId = userId;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public boolean isPlaylistPublic() {
        return playlistPublic;
    }

    public void setPlaylistPublic(boolean playlistPublic) {
        this.playlistPublic = playlistPublic;
    }

    public ArrayList<song_HE150883> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<song_HE150883> songs) {
        this.songs = songs;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
