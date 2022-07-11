/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author smileymask
 */
public class rate_He150883 {
    private String userId;
    private String Id;
    private int Score;

    public rate_He150883() {
    }

    public rate_He150883(String userId, String Id, int Score) {
        this.userId = userId;
        this.Id = Id;
        this.Score = Score;
    }

    
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int Score) {
        this.Score = Score;
    }
    
    
}
