/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author smileymask
 */
public class User_HE150883 {
    private String userId;
    private String userPassword;
    private String userName;
    private boolean userGender;
    private String userEmail;
    private boolean userActive;
    private String userPhone;
    private String userAddress;
    private int userType;
    private Date userEnroll;

    public User_HE150883() {
    }

    public User_HE150883(String userId, String userPassword, String userName, boolean userGender, String userEmail, boolean userActive, String userPhone, String userAddress, int userType, Date userEnroll) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userGender = userGender;
        this.userEmail = userEmail;
        this.userActive = userActive;
        this.userPhone = userPhone;
        this.userAddress = userAddress;
        this.userType = userType;
        this.userEnroll = userEnroll;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isUserGender() {
        return userGender;
    }

    public void setUserGender(boolean userGender) {
        this.userGender = userGender;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public boolean isUserActive() {
        return userActive;
    }

    public void setUserActive(boolean userActive) {
        this.userActive = userActive;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public Date getUserEnroll() {
        return userEnroll;
    }

    public void setUserEnroll(Date userEnroll) {
        this.userEnroll = userEnroll;
    }
    
    
    
}
