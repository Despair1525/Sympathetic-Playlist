/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import java.sql.*;
import model.*;
import java.util.*;
import java.sql.Date;

/**
 *
 * @author smileymask
 */
public class DAOUser {

    private Connection con;
    public ArrayList<User_HE150883> userList;
    public String status;

    public DAOUser() {
        try {
            con = new DBContext().getConnection();

        } catch (Exception e) {
            status = "Error " + e.getMessage();
        }
    }

    public ArrayList<User_HE150883> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<User_HE150883> userList) {
        this.userList = userList;
    }

    public void loadUser() {
        userList = new ArrayList<>();
        String sql = "select * from User_HE150883";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String userId = rs.getString("userId"); // get by column name or index of column
                String userPassword = rs.getString("userPassword");
                String userName = rs.getString("userName");
                boolean userGender = rs.getBoolean("userGender");
                String userEmail = rs.getString("userEmail");
                boolean userActive = rs.getBoolean("userActive");
                String userPhone = rs.getString("userPhone");
                String userAddress = rs.getString("userAddress");
                int userType = rs.getInt("userType");
                Date userEnroll = rs.getDate("userEnroll");
                userList.add(new User_HE150883(userId, userPassword, userName, userGender, userEmail, userActive, userPhone, userAddress, userType, userEnroll));
            }
        } catch (Exception e) {
            status = "Error Load " + e.getMessage();
        }
    }

    public void insertUser(String userId, String userPassword, String userName, boolean userGender, String userMail, String userPhone, String userAddress) {

        String sql = "insert into User_HE150883(userId,userPassword,userName,userGender,userEmail,userPhone,userAddress) "
                + "VALUES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, userId);
            ps.setString(2, MD5.getMd5(userPassword));
            ps.setString(3, userName);
            ps.setBoolean(4, userGender);
            ps.setString(5, userMail);
            ps.setString(6, userPhone);
            ps.setString(7, userAddress);

            ps.execute();
            status = "Register Succsess";
        } catch (Exception e) {
            status = ("Error " + e.getMessage());
        }

    }
    public void updatePass(String userId,String pass){
        
     String sql = "UPDATE User_HE150883 SET userPassword = ? WHERE userId =?   ";
       try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, MD5.getMd5(pass));
            ps.setString(2, userId);
            ps.execute();
            status = "Register Succsess";
        } catch (Exception e) {
            status = ("Error " + e.getMessage());
        }

    };

    public void updateUser(String userId,String userName,boolean userGender,String userMail,String userPhone,String userAddress) {
    String sql = "UPDATE User_HE150883 SET  "
                + "userName = ? , userGender = ?, userEmail = ? , userPhone = ?, userAddress = ? WHERE userId =? ";
    
    try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, userName);
            ps.setBoolean(2, userGender);
            ps.setString(3, userMail);
            ps.setString(4, userPhone);
            ps.setString(5, userAddress);
            ps.setString(6, userId);

            ps.execute();
            status = "Update User profile Succsess";
        } catch (Exception e) {
            status = ("Error " + e.getMessage());
        }

    
    };
    
     public User_HE150883 checkUser(String user, String pass) {
        
            pass = MD5.getMd5(pass);
        
        for (User_HE150883 obj : userList) {
            if (obj.getUserId().equals(user) && obj.getUserPassword().equals(pass)) {
                return obj;
            };

        };

        return null;
    }
     
     public User_HE150883 checkUserEmail(String user, String email) {
        for (User_HE150883 obj : userList) {
            if (obj.getUserId().equals(user) && obj.getUserEmail().equals(email)) {
                return obj;
            };

        };

        return null;
    }

    public User_HE150883 getUser(String id) {
        for (User_HE150883 user : userList) {
            if (user.getUserId().equals(id)) {
                return user;
            }

        }
        return null;
    }
public String genCaptcha() {

        // Khai bao 1 mang gom tat ca cac ki tu: chu thuong, chu hoa va so
        char data[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        String captcha = "";
        Random random = new Random();
        while (captcha.length() < 4) {
            int number = random.nextInt(data.length);
            String character = "" + data[number];
            // Neu captcha khong chua ki tu "character" roi thi captcha + them "character"
            if (!captcha.contains(character)) {
                captcha += character;
            }
            // Neu khong co thi tiep tuc quay lai chay vong while
        }

        return captcha;
    }



}
